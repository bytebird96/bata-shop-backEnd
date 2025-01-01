package com.shop.betashop.util;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private String SECRET_KEY = "betashop";
    private final long TOKEN_TIME = 60 * 60 * 100L; //10분


}
