package com.shop.betashop.dto;

import lombok.Data;

@Data
public class LoginRequest{
    private String userId;
    private String password;
}
