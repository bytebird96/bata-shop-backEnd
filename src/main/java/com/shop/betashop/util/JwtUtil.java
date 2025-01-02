package com.shop.betashop.util;

import com.shop.betashop.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;
    private final long TOKEN_TIME = 60 * 60 * 100L; //10분

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUserName());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        claims.put("Phone",user.getPhone());

        return Jwts.builder()
                .setClaims(claims) // Payload - 사용자 정보
                .setSubject(user.getUserName()) // 주제 설정 (보통 사용자 ID)
                .setIssuedAt(new Date()) // 토큰 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_TIME)) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 서명 알고리즘 및 비밀 키
                .compact(); // 토큰 생성
    }



}
