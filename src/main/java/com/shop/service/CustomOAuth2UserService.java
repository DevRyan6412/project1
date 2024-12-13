//package com.shop.service;
//
//import com.shop.entity.Member;
//import com.shop.constant.Role;
//import com.shop.dto.MemberFormDto;
//import org.springframework.beans.factory.annotation.Autowired;  // 이 부분 추가
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//import java.util.Map;
//
//@Service
//@Transactional
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        String email = null;
//        String name = null;
//
//        if ("google".equals(registrationId)) {
//            email = oAuth2User.getAttribute("email");
//            name = oAuth2User.getAttribute("name");
//        } else if ("kakao".equals(registrationId)) {
//            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
//            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//            email = (String) kakaoAccount.get("email");
//            name = (String) profile.get("nickname");
//        } else if ("naver".equals(registrationId)) {
//            Map<String, Object> response = oAuth2User.getAttribute("response");
//            email = (String) response.get("email");
//            name = (String) response.get("name");
//        }
//
//        try {
//            Member findMember = memberService.findByEmail(email);
//            if(findMember == null) {
//                MemberFormDto memberFormDto = new MemberFormDto();
//                memberFormDto.setEmail(email);
//                memberFormDto.setName(name);
//                memberFormDto.setPassword(UUID.randomUUID().toString());
//
//                Member member = Member.createMember(memberFormDto, passwordEncoder);
//                member.setRole(Role.USER);
//                memberService.saveMember(member);
//            }
//        } catch (Exception e) {
//            throw new OAuth2AuthenticationException("Authentication failed: " + e.getMessage());
//        }
//
//        return oAuth2User;
//    }
//}
//

package com.shop.service;

import com.shop.entity.Member;
import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 부모 클래스의 메서드를 호출하여 기본적인 사용자 정보를 가져옵니다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = null;
        String name = null;

        // OAuth2 공급자별로 사용자 정보 추출
        if ("google".equals(registrationId)) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
        } else if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("nickname");
        } else if ("naver".equals(registrationId)) {
            Map<String, Object> response = oAuth2User.getAttribute("response");
            email = (String) response.get("email");
            name = (String) response.get("name");
        }

        // 이메일이 null인지 확인
        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationException("OAuth2 공급자로부터 이메일 정보를 가져올 수 없습니다.");
        }

        try {
            // 이메일로 회원 조회
            Member findMember = memberService.findByEmail(email);
            if (findMember == null) {
                // 신규 회원 가입 처리
                MemberFormDto memberFormDto = new MemberFormDto();
                memberFormDto.setEmail(email);
                memberFormDto.setName(name);
                memberFormDto.setPassword(UUID.randomUUID().toString());

                Member member = Member.createMember(memberFormDto, passwordEncoder);
                member.setRole(Role.USER);
                memberService.saveMember(member);
            }
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("OAuth2 인증 과정에서 오류가 발생했습니다: " + e.getMessage());
        }

        // 권한 설정
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // 사용자 정보를 포함한 DefaultOAuth2User 객체 생성 및 반환
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("email", email); // 이메일 정보 추가

        return new DefaultOAuth2User(authorities, attributes, "email");
    }
}
