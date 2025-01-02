package com.shop.betashop.service;

import com.shop.betashop.dto.LoginRequest;
import com.shop.betashop.dto.LoginResponse;
import com.shop.betashop.model.User;
import com.shop.betashop.repository.UserRepository;
import com.shop.betashop.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 로그인 검증 로직 생성
     * @param loginRequest
     * @return
     */
    public LoginResponse authenticate(LoginRequest loginRequest){
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();

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

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Invalid user id");
        }

        LoginResponse loginResponse = new LoginResponse(jwtUtil.generateToken(user));

        return loginResponse;
    }

    /**
     * 이메일인지 확인
     * @param input
     * @return
     */
    private boolean isEmail(String input) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return input.matches(emailRegex);
    }

    /**
     * 전화번호인지 확인
     * @param input
     * @return
     */
    private boolean isPhoneNumber(String input) {
        String phoneRegex = "^\\d{10,15}$"; // 10~15 자리 숫자로 구성된 전화번호
        return input.matches(phoneRegex);
    }
}
