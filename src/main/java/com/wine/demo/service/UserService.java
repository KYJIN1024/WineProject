package com.wine.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    
    // 비밀번호 암호화를 위한 PasswordEncoder 인스턴스
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
    
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    // 로그인 처리를 담당하는 메서드  사용자 이름과 비밀번호로 로그인을 시도하고, 인증 결과를 반환합니다.
    public boolean loginUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
            return authentication.isAuthenticated();
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return false;
        }
    }
    
    // 사용자 이름으로 사용자 정보를 조회합니다.
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    //사용자 이메일로 사용자 정보를 조회합니다.
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // 사용자 이름이 사용 가능한지 확인합니다.
    public boolean checkUsernameAvailability(String username) {
        User user = userRepository.findByUsername(username);
        return user == null;
    }

    // 새로운 사용자를 저장하거나 기존 사용자 정보를 업데이트합니다.
    @Transactional
    public void save(User user, boolean encodePassword) {
        if (encodePassword) {
            logger.debug("Encoding password for user: {}", user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        try {
            userRepository.save(user);
            logger.info("User saved successfully: {}", user.getUsername());
        } catch (Exception e) {
            logger.error("Error saving user: {}", user, e);
            throw e;
        }
    }
    // 사용자의 현재 비밀번호가 맞는지 확인합니다.
    public boolean checkCurrentPassword(String currentPassword) {
        User user = getAuthenticatedUser();
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }

    // 현재 인증된 사용자를 가져옵니다.
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthenticatedUserException("인증이 필요합니다.");
        }
        return userRepository.findByUsername(authentication.getName());
    }
    
    // 인증 코드를 랜덤하게 생성합니다.
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
    
    //  인증 이메일을 발송합니다.
    public void sendVerificationEmail(String email) {
        try {    
            String verificationCode = generateVerificationCode();
            String message = "인증 코드: " + verificationCode;

           
            VerificationCode code = new VerificationCode(email, verificationCode);
            verificationCodeRepository.save(code);

            emailService.sendSimpleMessage(email, "인증 코드", message);

           // logger.info("Sent verification email to {}", email);
        } catch (MailException e) {
           // logger.error("{}애 이메일인증전송을 실패했습니다.", email, e);
            throw e;
        } catch (Exception e) {
            //logger.error("알수없는 에러가 발생했습니다.", email, e);
            throw e;
        }
    }
    
    public boolean verifyEmailCode(String email, String verificationCode) {
        VerificationCode codeEntity = verificationCodeRepository.findByCodeAndEmail(verificationCode, email);
        if (codeEntity != null) {
            //verificationCodeRepository.delete(codeEntity);
            return true;
        } else {
            return false;
        }
    }
    
    // 인증 코드를 검증하여 해당 인증 코드 엔터티를 반환합니다.
    public VerificationCode findVerificationCode(String code, String email) {
        return verificationCodeRepository.findByCodeAndEmail(code, email);
    }
    
    // 사용자 정보를 업데이트 합니다.
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
    
    // 과거 날짜/시간으로부터 지금까지의 경과 시간을 문자열로 반환합니다.
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
    
    // 유저가 좋아요버튼을 누른 생산자 목록을 반환합니다.
    public List<ProducerEntity> getLikedProducers(String username) {
        User user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        // 좋아요한 게시글 목록을 반환하는 로직 추가
        return producerlikeRepository.findProducersByUserId(user.getId());
    }

    // 유저가 좋아요버튼을 누른 샵,레스토랑 목록을 반환합니다.
    public List<ShopEntity> getLikedShops(String username) {
        User user = findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        // 좋아요한 게시글 목록을 반환하는 로직 추가
        return shoplikeRepository.findShopsByUserId(user.getId());
    }
    
    // 비밀번호 재설정 토큰 생성 및 저장합니다.
    public String createPasswordResetTokenForUser(User user) {
        // 보안에 강한 랜덤 토큰을 생성합니다.
        String token = UUID.randomUUID().toString();
        
        // user 엔터티에 토큰을 저장합니다.
        user.setResetToken(token); // User 엔터티에 resetToken 필드가 추가되어야 합니다.
        userRepository.save(user); // 변경사항을 데이터베이스에 반영합니다.
        
        return token;
    }

    // 비밀번호 재설정 이메일 발송합니다.
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
    
    public User saveOrUpdateUser(User user) {
        // 사용자 정보 저장 또는 업데이트
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username);
        }
    }

	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
	    return user != null;
	}
	
    
}