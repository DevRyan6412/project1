package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter @ToString
public class Member extends BaseEntity {

    @Id
//    @Column(name="member_id")
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDENTITY 전략 사용
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "business_registration_number")
    private String businessNumber;

    @Column(nullable = false, columnDefinition = "integer default 0")  // default 값 설정
//    @Column(nullable = false)  // 민혁
    private int availableMileage = 0;

    // createMember 메서드는 그대로 유지
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(memberFormDto.getRole());
        member.setBusinessNumber(memberFormDto.getBusinessNumber());
        member.setAvailableMileage(0);
        return member;
    }
}