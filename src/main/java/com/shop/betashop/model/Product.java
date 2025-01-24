package com.shop.betashop.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String title; // 제목 겸 설명
    private String image; // 이미지 경로
    private int evaluation; // 평가
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
