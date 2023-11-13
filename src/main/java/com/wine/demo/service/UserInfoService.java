package com.wine.demo.service;

import com.wine.demo.entity.CommentEntity;
import com.wine.demo.entity.FreeBoardEntity;
import com.wine.demo.model.User;
import com.wine.demo.repository.UserRepository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@DependsOn("passwordEncoder")
public class UserInfoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PersistenceContext
    private EntityManager entityManager;
    

    

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("현재 로그인한 사용자를 찾을 수 없습니다.");
        }
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername);
        if (user == null) {
            throw new UsernameNotFoundException("현재 로그인한 사용자의 정보가 데이터베이스에 없습니다.");
        }
        return user;
    }

    @Transactional
    public void updateEmail(String email) {
        User user = getCurrentUser();
        try {
            user.setEmail(email);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("이메일 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional
    public void updatePassword(String newPassword) {
        User user = getCurrentUser();
        try {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 업데이트 중 오류가 발생했습니다.", e);
        }
    }
    
    public int getUserPostCount(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM freeboard WHERE frboardwriter = ?", 
                Integer.class, 
                username
        );
    }

    public int getUserCommentCount(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM comment WHERE writer = ?", 
                Integer.class, 
                username
        );
    }
    
    public List<FreeBoardEntity> getUserPosts(String username) {
        return jdbcTemplate.query("SELECT * FROM freeboard WHERE frboardwriter = ?", new Object[]{username}, new BeanPropertyRowMapper<>(FreeBoardEntity.class));
    }

    public List<CommentEntity> getUserComments(String username) {
        return entityManager.createQuery("SELECT c FROM CommentEntity c JOIN FETCH c.freeBoard WHERE c.writer = :username", CommentEntity.class)
                            .setParameter("username", username)
                            .getResultList();
    }
   
    
    
    
}