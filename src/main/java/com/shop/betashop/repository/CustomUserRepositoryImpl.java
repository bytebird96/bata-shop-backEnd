package com.shop.betashop.repository;

import com.shop.betashop.dto.DuplicateFieldInfo;
import com.shop.betashop.dto.SignupRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository //확장시키는 경우 Repository 수동으로 빈에 등록해야함 기존 것들은 자동 등록됨
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean findUserByEmailOrPhone(String userId) {
        String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.email = :userId OR u.phone = :userId";
        return em.createQuery(jpql, Boolean.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public DuplicateFieldInfo findDuplicateFieldInfo(SignupRequest signupRequest) {
        String jpql = "SELECT new com.shop.betashop.dto.DuplicateFieldInfo( " +
                "    CASE " +
                "        WHEN COUNT(u.email) > 1 THEN 'Email' " +
                "        WHEN COUNT(u.phone) > 1 THEN 'Phone' " +
                "    END, " +
                "    COALESCE(u.email, 'N/A'), " +
                "    COALESCE(u.phone, 'N/A'), " +
                "    COUNT(*) " +
                ") " +
                "FROM User u " +
                "WHERE u.email = :email OR u.phone = :phone " +
                "GROUP BY u.email, u.phone " +
                "HAVING COUNT(*) > 1";

        return em.createQuery(jpql, DuplicateFieldInfo.class)
                .setParameter("email", signupRequest.getEmail())
                .setParameter("phone", signupRequest.getPhone())
                .getResultStream()
                .findFirst()  // 결과가 없으면 null 반환
                .orElse(null);
    }


}
