package com.wine.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wine.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	 // 사용자 이름으로 사용자를 찾는 메서드
    User findByUsername(String username);
    // 이메일로 사용자를 찾는 메서드
    User findByEmail(String email);

    default void updateUser(User user) {
        save(user); // JpaRepository에서 제공하는 save 메서드를 사용하여 엔터티의 변경사항을 데이터베이스에 반영
    }
    
    User findByResetToken(String resetToken);
    
}