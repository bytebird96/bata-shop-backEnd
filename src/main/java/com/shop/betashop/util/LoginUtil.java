package com.shop.betashop.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class LoginUtil {
    // 이메일 정규식 (일반적인 이메일 형식 검사)
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // 한국 전화번호 정규식 (010-1234-5678 또는 01012345678 형식)
    private static final String PHONE_REGEX = "^(01[016789])-?\\d{3,4}-?\\d{4}$";

    // 공백 및 문자 제거 후 검증
    public static String validateContactInfo(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "입력값이 비어 있습니다.";
        }

        // 입력값 공백 제거 및 문자 제거 (숫자와 @, ., - 만 허용)
        String cleanedInput = input.trim().replaceAll("[^0-9a-zA-Z@.-]", "");

        // 이메일 검증
        if (Pattern.matches(EMAIL_REGEX, cleanedInput)) {
            return "Success";
        }

        // 전화번호 검증
        if (Pattern.matches(PHONE_REGEX, cleanedInput)) {
            return "Success";
        }

        // 둘 다 아니면 유효성 검사 불가
        return "유효한 로그인 형식이 아닙니다.";
    }
}
