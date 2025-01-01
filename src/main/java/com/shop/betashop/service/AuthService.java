package com.shop.betashop.service;

import com.shop.betashop.dto.LoginRequest;
import com.shop.betashop.dto.LoginResponse;
import com.shop.betashop.model.User;
import com.shop.betashop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse authenticate(LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse("");

        String userId = loginRequest.getUserId();

        User user;
        if(isEmail(userId)){
            user = userRepository.findByUserEmail(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email address"));
        }else if (isPhoneNumber(userId)){
            user = userRepository.findByUserPhone(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid phone number"));
        }else{
            throw new IllegalArgumentException("Invalid user id");
        }



        return loginResponse;
    }

    // 이메일인지 확인하는 메서드
    private boolean isEmail(String input) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return input.matches(emailRegex);
    }

    // 전화번호인지 확인하는 메서드
    private boolean isPhoneNumber(String input) {
        String phoneRegex = "^\\d{10,15}$"; // 10~15 자리 숫자로 구성된 전화번호
        return input.matches(phoneRegex);
    }
}
