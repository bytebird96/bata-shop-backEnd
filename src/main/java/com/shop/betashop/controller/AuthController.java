package com.shop.betashop.controller;

import com.shop.betashop.dto.LoginRequest;
import com.shop.betashop.dto.LoginResponse;
import com.shop.betashop.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4000")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;

    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        //회원 정보 넘김
        LoginResponse loginResponse = authService.authenticate(loginRequest);
        //토큰 정보 리턴
        return ResponseEntity.ok(loginResponse);
    }
}
