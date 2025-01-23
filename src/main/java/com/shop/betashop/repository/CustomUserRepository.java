package com.shop.betashop.repository;

import com.shop.betashop.dto.DuplicateFieldInfo;
import com.shop.betashop.dto.SignupRequest;

public interface CustomUserRepository {
    Boolean findUserByEmailOrPhone(String userId);
    DuplicateFieldInfo findDuplicateFieldInfo(SignupRequest signupRequest);
}
