package com.wine.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wine.demo.exception.UserNotFoundException;
import com.wine.demo.model.User;
import com.wine.demo.model.VerificationCode;
import com.wine.demo.repository.UserRepository;
import com.wine.demo.repository.VerificationCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import com.wine.demo.service.EmailService;

@Service
public class UserService {

	    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	    @Autowired
	    private UserRepository userRepository;
	    
	    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	    
	    public User authenticate(String username, String password) {
	        User user = userRepository.findByUsername(username);
	        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	            return user;
	        }
	        return null;
	    }
	    
	    
	    @Autowired
	    private EmailService emailService;  
	
	    @Autowired
	    private VerificationCodeRepository verificationCodeRepository;  
	
	    public void save(User user, boolean encodePassword) {
	        try {
	            if (encodePassword) {
	                user.setPassword(passwordEncoder.encode(user.getPassword()));
	            }
	            User savedUser = userRepository.save(user);
	            logger.info("User saved with id: {}", savedUser.getId());
	        } catch (Exception e) {
	            logger.error("Failed to save user: {}", e.getMessage());
	            throw new RuntimeException(e); // 추가된 부분
	        }
	    }
	
	    
	    
	    private String generateVerificationCode() {
	        Random random = new Random();
	        int code = 100000 + random.nextInt(900000);
	        return String.valueOf(code);
	    }
	    
	    public void sendVerificationEmail(String email) {
	        try {
	            String verificationCode = generateVerificationCode();
	            String message = "인증번호: " + verificationCode;

	            emailService.sendSimpleMessage(email, "KOWASA 회원가입 인증번호", message);

	            verificationCodeRepository.save(new VerificationCode(email, verificationCode));
	        } catch (Exception e) {
	            logger.error("Failed to send verification email: {}", e.getMessage());
	            throw new RuntimeException(e); // 추가된 부분
	        }
	    }
	
	    public VerificationCode findVerificationCode(String code, String email) {
	        return verificationCodeRepository.findByCodeAndEmail(code, email);
	    }
	
	    public User findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }
	    
	    public User findByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }
	    
	    public boolean checkUsernameAvailability(String username) {
	        User user = userRepository.findByUsername(username);
	        return user == null;
	    }
	
	   
	    }