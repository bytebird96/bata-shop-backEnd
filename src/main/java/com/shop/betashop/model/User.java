package com.shop.betashop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String password;
    private String userName;
    private String email;
    private String phone;
    //관리자 여부
    @Enumerated(EnumType.STRING)
    private UserRoleType role;
}
