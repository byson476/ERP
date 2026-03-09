package com.erp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
