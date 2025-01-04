package com.shop.betashop.config;

import com.shop.betashop.model.UserRoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        return RoleHierarchyImpl.withRolePrefix("ROLE_").role(UserRoleType.ADMIN.toString())
//                .implies(UserRoleType.USER.toString()).build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.disable()) // 전역 CORS 설정 활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**", "/images/**").permitAll() // API 경로 허용
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                );

        return http.build();
    }
}