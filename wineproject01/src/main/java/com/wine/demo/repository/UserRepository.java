package com.wine.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wine.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
