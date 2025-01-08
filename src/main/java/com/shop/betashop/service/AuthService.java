package com.shop.betashop.service;

import com.shop.betashop.dto.*;
import com.shop.betashop.model.User;
import com.shop.betashop.repository.CustomUserRepository;
import com.shop.betashop.repository.UserRepository;
import com.shop.betashop.util.JwtUtil;
import com.shop.betashop.util.MessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil
    , CustomUserRepository customUserRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.customUserRepository = customUserRepository;
    }

    /**
     * 로그인 검증 로직 생성
     * @param loginRequest
     * @return
     */
    @Transactional(readOnly = true)
    public LoginResponse authenticate(LoginRequest loginRequest){
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();

        User user;
        if(isEmail(userId)){
            user = userRepository.findByEmail(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email address"));
        }else if (isPhoneNumber(userId)){
            user = userRepository.findByPhone(userId)
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
     * 사용자 존재 여부 판단
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isUserExist(String userId){
        boolean isUserExist = false;
        isUserExist = customUserRepository.findUserByEmailOrPhone(userId);
        return isUserExist;
    }

    /**
     * 회원가입
     * @param signupRequest
     * @return
     */
    @Transactional
    public SignupResponse userRegister(SignupRequest signupRequest){
        SignupResponse signupResponse = new SignupResponse();

        DuplicateFieldInfo duplicateFieldInfo = customUserRepository.findDuplicateFieldInfo(signupRequest);
        if (duplicateFieldInfo != null) {
            logger.info("Duplicate Type: {}", duplicateFieldInfo.getDuplicateType());
            logger.info("Duplicate Email: {}", duplicateFieldInfo.getEmail());
            logger.info("Duplicate Phone: {}", duplicateFieldInfo.getPhone());
            logger.info("Duplicate Count: {}", duplicateFieldInfo.getDuplicateCount());

            if(duplicateFieldInfo.getDuplicateType().equals("Email")){
                signupResponse.setMessage(MessageProvider.DUPLICATE_EMAIL);
            }else{
                signupResponse.setMessage(MessageProvider.DUPLICATE_PHONE);
            }
            return signupResponse;
        }

        User user = new User();

        //사용자 정보 세팅
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserName(signupRequest.getUserName());

        //저장
        user = userRepository.save(user);
        signupResponse.setToken(jwtUtil.generateToken(user));
        signupResponse.setMessage(MessageProvider.USER_REGISTER_SUCCESS);

        return signupResponse;
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
