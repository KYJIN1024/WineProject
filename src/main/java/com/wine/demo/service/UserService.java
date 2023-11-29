package com.wine.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.entity.ShopEntity;
import com.wine.demo.exception.UnauthenticatedUserException;
import com.wine.demo.model.User;
import com.wine.demo.model.VerificationCode;
import com.wine.demo.repository.CommentRepository;
import com.wine.demo.repository.FreeBoardRepository;
import com.wine.demo.repository.ProducerLikeRepository;
import com.wine.demo.repository.ShopLikeRepository;
import com.wine.demo.repository.UserRepository;
import com.wine.demo.repository.VerificationCodeRepository;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    
    @Autowired
    private FreeBoardRepository freeboardRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ProducerLikeRepository producerlikeRepository;
    
    @Autowired
    private ShopLikeRepository shoplikeRepository;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    // 로그인 처리를 담당하는 메서드
    public boolean loginUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
            return authentication.isAuthenticated();
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return false;
        }
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

    public void save(User user, boolean encodePassword) {
        if (encodePassword) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public boolean checkCurrentPassword(String currentPassword) {
        User user = getAuthenticatedUser();
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthenticatedUserException("인증이 필요합니다.");
        }
        return userRepository.findByUsername(authentication.getName());
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendVerificationEmail(String email) {
        try {    
            String verificationCode = generateVerificationCode();
            String message = "인증 코드: " + verificationCode;

            // Save the generated verification code to the database
            VerificationCode code = new VerificationCode(email, verificationCode);
            verificationCodeRepository.save(code);

            emailService.sendSimpleMessage(email, "인증 코드", message);

           // logger.info("Sent verification email to {}", email);
        } catch (MailException e) {
           // logger.error("Failed to send verification email to {}", email, e);
            throw e;
        } catch (Exception e) {
            //logger.error("An unexpected error occurred when trying to send verification email to {}", email, e);
            throw e;
        }
    }

    public VerificationCode findVerificationCode(String code, String email) {
        return verificationCodeRepository.findByCodeAndEmail(code, email);
    }
    
    public void updateUser(String username, String newEmail, String currentPassword, String newPassword, String verificationCode) {
        User user = this.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        
        if (!this.checkCurrentPassword(currentPassword)) {
            throw new BadCredentialsException("현재 비밀번호가 틀렸습니다.");
        }
        
        VerificationCode code = this.findVerificationCode(verificationCode, newEmail);
        if (code == null) {
            throw new BadCredentialsException("잘못된 인증 코드입니다.");
        }
        
        user.setEmail(newEmail);
        user.setPassword(this.passwordEncoder.encode(newPassword));
        
        this.userRepository.save(user);
    }
    
    public static String getTimeAgo(LocalDateTime past) {
        Duration duration = Duration.between(past, LocalDateTime.now());
        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + "초 전";
        } else if (seconds < 3600) {
            return seconds / 60 + "분 전";
        } else if (seconds < 86400) {
            return seconds / 3600 + "시간 전";
        } else {
            return seconds / 86400 + "일 전";
        }
    }
    
    public List<ProducerEntity> getLikedProducers(String username) {
        User user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        // 좋아요한 게시글 목록을 반환하는 로직 추가
        return producerlikeRepository.findProducersByUserId(user.getId());
    }

    public List<ShopEntity> getLikedShops(String username) {
        User user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        // 좋아요한 게시글 목록을 반환하는 로직 추가
        return shoplikeRepository.findShopsByUserId(user.getId());
    }
    
 // 비밀번호 재설정 토큰 생성 및 저장
    public String createPasswordResetTokenForUser(User user) {
        // 보안에 강한 랜덤 토큰을 생성합니다.
        String token = UUID.randomUUID().toString();
        
        // user 엔터티에 토큰을 저장합니다.
        user.setResetToken(token); // User 엔터티에 resetToken 필드가 추가되어야 합니다.
        userRepository.save(user); // 변경사항을 데이터베이스에 반영합니다.
        
        return token;
    }

    // 비밀번호 재설정 이메일 발송
    public void sendPasswordResetEmail(User user, String token) throws MessagingException {

        // 현재 시간 설정
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedNow = now.format(formatter);

        // 비밀번호 변경 링크
        String resetLink = "http://localhost:8080/changePw?token=" + token;

        // 이메일 메시지 구성
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(user.getEmail());
        helper.setSubject("코와사 비밀번호 재설정 메일");

        // HTML 메시지 설정
        String htmlMsg = "<p>안녕하세요.</p>" +
                         "<p>회원님은 " + formattedNow + "에 비밀번호 찾기 요청을 하셨습니다.</p>" +
                         "<p>코와사는 특정 회원의 비밀번호를 확인할 수 없기 때문에 아래 버튼을 통해 비밀번호를 초기화해주세요.</p>" +
                         "<p>회원 아이디: " + user.getUsername() + "</p>" +
                         "<a href='" + resetLink + "' style='color: white; text-decoration: none; padding: 10px 20px; background-color: #1a73e8; border-radius: 5px; display: inline-block;'>비밀번호 변경</a>";

        // HTML 형식으로 메시지를 설정합니다.
        helper.setText(htmlMsg, true);

        // 메일 발송
        mailSender.send(message);
    }

    // 비밀번호 재설정 토큰으로 사용자를 검색하는 메서드
    public User getUserByPasswordResetToken(String token) {
        return userRepository.findByResetToken(token);
    }
    
    // 사용자의 비밀번호를 변경하는 메서드
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    
}