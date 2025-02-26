package com.shop.betashop.controller;

import com.shop.betashop.dto.LoginRequest;
import com.shop.betashop.dto.LoginResponse;
import com.shop.betashop.dto.SignupRequest;
import com.shop.betashop.dto.SignupResponse;
import com.shop.betashop.service.AuthService;
import com.shop.betashop.util.LoginUtil;
import com.shop.betashop.util.MessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:4000", "http://192.168.0.2:4000", "http://172.31.0.1:4000"})
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        //회원 정보 넘김
        LoginResponse loginResponse = authService.authenticate(loginRequest);
        //토큰 정보 리턴
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/isUserExist")
    public ResponseEntity<Map<String,String>> isUserExist(@RequestParam String userId) {
        String validate = LoginUtil.validateContactInfo(userId);
        Map<String, String> response = new HashMap<>();
        if(!validate.equals("Success")){
            response.put("code",MessageProvider.INVALID_INPUT);
            response.put("result","ERROR");
            return ResponseEntity.ok(response);
        }
        // 회원 아이디 존재 여부 판단
        Boolean isUserExist = authService.isUserExist(userId);
        if(!isUserExist){
            response.put("code",MessageProvider.NOT_FOUND_USER);
            response.put("result","FAIL");
            return ResponseEntity.ok(response);
        }
        response.put("code","");
        response.put("result","Success");

        return ResponseEntity.ok(response);
    }

    /**
     * 회원 가입
     * @param signupRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<SignupResponse> register(@RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = authService.userRegister(signupRequest);
        if (signupResponse.getMessage().equals(MessageProvider.DUPLICATE_EMAIL)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(signupResponse);
        }else if(signupResponse.getMessage().equals(MessageProvider.DUPLICATE_PHONE)){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(signupResponse);
        }

        return ResponseEntity.ok(signupResponse);
    }
}
