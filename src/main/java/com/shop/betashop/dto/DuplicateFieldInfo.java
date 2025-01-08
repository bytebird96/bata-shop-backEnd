package com.shop.betashop.dto;

public class DuplicateFieldInfo {
    private String duplicateType;
    private String email;
    private String phone;
    private Long duplicateCount;

    // 생성자
    public DuplicateFieldInfo(String duplicateType, String email, String phone, Long duplicateCount) {
        this.duplicateType = duplicateType;
        this.email = email;
        this.phone = phone;
        this.duplicateCount = duplicateCount;
    }

    // Getter
    public String getDuplicateType() { return duplicateType; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Long getDuplicateCount() { return duplicateCount; }
}
