package com.shop.betashop.repository;

import com.shop.betashop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM user u WHERE u.email = :userId OR u.phone = :userId)", nativeQuery = true)
    boolean existsByEmailOrPhoneNative(@Param("userId") String userId);
}
