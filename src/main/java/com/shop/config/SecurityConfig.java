package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {//스프링부트3 이상 버전부터 사용불가
//public class SecurityConfig {
    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

        http.authorizeRequests()
                .mvcMatchers("/", "/members/**","/item/**","/images/**","/api/mileage/summary", "/api/members/info").permitAll() // members,item,images뒤에 어떤 URL이 오든 허락한다
                .mvcMatchers("/admin/**").hasRole("ADMIN") //admin다음에 어떤 URL오든 admin자격을 가진유저만 허용
                .mvcMatchers("/order").authenticated() // 주문 API는 인증된 사용자만 접근 가능
//                .mvcMatchers("/api/mileage/summary", "/api/members/info").permitAll() // API 경로 인증 없이 허용
                .anyRequest().authenticated();
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    //    return http.build();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
        //css,js,img 가 들어간 URL은 인증 무시
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
