package com.example.noticeboard.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/login", "/api/users/signup", "/h2-console/**", "/posts").permitAll()  // 로그인, 회원가입 페이지와 H2 콘솔 접근 허용
                        .anyRequest().authenticated()  // 그 외의 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/api/users/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/posts", true)
                        .permitAll()  // 로그인 페이지는 모두 접근 가능
                )
                .logout(logout -> logout
                        .permitAll()  // 로그아웃 페이지는 모두 접근 가능
                )
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/h2-console/**", "/api/users/signup", "/api/users/login")  // H2 콘솔에서 CSRF 보호 비활성화
//                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .disable()  // 모든 보안 헤더를 비활성화 (H2 콘솔에서 iframe 사용을 허용하기 위해)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 세션 생성 정책 설정
                );

        return http.build();
    }
}
