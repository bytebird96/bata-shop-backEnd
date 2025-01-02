package com.shop.betashop.model;

import lombok.Getter;

@Getter
public enum UserRoleType {
    ADMIN("관리자"),
    USER("유저"),
    SELLER("판매자");

    private final String description;

    private UserRoleType(String description) {
        this.description = description;
    }
}
