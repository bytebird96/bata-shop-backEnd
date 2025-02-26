package com.shop.betashop.util;

import org.springframework.stereotype.Component;

@Component
public class MessageProvider {
    public static final String DUPLICATE_EMAIL = "이메일이 이미 존재합니다.";
    public static final String DUPLICATE_PHONE = "전화번호가 이미 존재합니다.";
    public static final String USER_REGISTER_SUCCESS = "회원가입 성공";
    public static final String INVALID_INPUT = "입력값이 유효하지 않습니다.";
    public static final String NOT_FOUND_USER = "사용자를 찾을 수 없습니다.";
}
