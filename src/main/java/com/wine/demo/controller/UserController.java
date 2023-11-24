package com.wine.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wine.demo.exception.UserNotFoundException;
import com.wine.demo.model.User;
import com.wine.demo.model.VerificationCode;
import com.wine.demo.service.UserService;
import java.util.HashMap;

import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;



@Controller
public class UserController {
	
	 private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	 
	 @Autowired
	 private UserDetailsService userDetailsService;

	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, HttpSession session) {
    	 String referrer = request.getHeader("Referer");
    	    if (referrer != null && !referrer.contains("/login")) {
    	        session.setAttribute("prevPage", referrer);
    	    }
    	    return "login/login";
    	}

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (username == null || password == null) {
            model.addAttribute("error", "Missing username or password");
            return "login";
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
            if (authentication.isAuthenticated()) {
                session.setAttribute("username", username);  
                logger.info("User {} has been successfully authenticated.", username);  // Successful login message
                
             // 세션에서 이전 페이지 URL 가져오기
                String prevPage = (String) session.getAttribute("prevPage");
                session.removeAttribute("prevPage"); // 사용 후 세션에서 삭제
                logger.info("Redirecting to previous page: {}", prevPage);
                return "redirect:" + (prevPage != null ? prevPage : "/"); // 이전 페이지가 없는 경우 홈으로 리디렉션
            } else {
                model.addAttribute("error", "Invalid password");
                return "login";
            }
        } catch (UsernameNotFoundException ex) {
            logger.error("Failed to login. No user found with username: {}", username);
            model.addAttribute("error", "User not found");
            return "login";
        } catch (BadCredentialsException ex) {
            logger.error("Failed to login. Bad credentials: {}", ex.getMessage());
            model.addAttribute("error", "Invalid password");
            return "login";
        } catch (Exception ex) {
            logger.error("Failed to login. Unexpected error: {}", ex.getMessage());
            model.addAttribute("error", "Unexpected error");
            return "login";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "login/register";
    }
    
    @PostMapping("/checkUsername")
    @ResponseBody
    public ResponseEntity<Boolean> checkUsername(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        boolean isAvailable = userService.checkUsernameAvailability(username);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
    
    @PostMapping("/sendVerificationEmail")
    @ResponseBody
    public ResponseEntity<String> sendVerificationEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        try {
            userService.sendVerificationEmail(email);
        } catch (MailException e) {
            return new ResponseEntity<>("이메일 전송에 실패했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("서버 에러: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("이메일 전송 완료", HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseBody
    public String processRegister(@RequestBody Map<String, String> payload) {
        logger.info("Received registration request with payload: {}", payload);
        String username = payload.get("username");
        String password = payload.get("password");
        String passwordConfirm = payload.get("passwordConfirm");
        String email = payload.get("email");
        String verificationCode = payload.get("verificationCode");

        if (!password.equals(passwordConfirm)) {
            return "register";
        }

        VerificationCode code = userService.findVerificationCode(verificationCode, email);
        logger.info("VerificationCode: {}", code);

        if (code != null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setEnabled(true);
            user.setRole("USER");
            userService.save(user, true);
            return "redirect:/login";
        } else {
            return "register";
        }
    }
    
    @GetMapping("/findIdPw")
    public String showFindIdPwPage() {
        return "login/findIdPw";
    }
    
    @PostMapping("/findUsername")
    @ResponseBody
    public ResponseEntity<String> findUsername(@RequestParam Map<String, String> payload) {
        String email = payload.get("email");
        User user = userService.findByEmail(email);
        if(user != null) {
            return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당아이디의 이메일주소를 찾을수 없습니다..", HttpStatus.NOT_FOUND);
        }
    }

    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/resetPassword")
    public String handleResetPasswordRequest(@RequestParam String email, Model model) {
        User user = userService.findByEmail(email);
        if (user != null) {
            try {
                // 토큰 생성 및 사용자에 저장
                String token = userService.createPasswordResetTokenForUser(user);
                // 비밀번호 재설정 이메일 발송
                userService.sendPasswordResetEmail(user, token);
                // 비밀번호 재설정 요청이 성공적으로 처리되었다는 메시지 표시
                model.addAttribute("message", "비밀번호 재설정 이메일이 발송되었습니다.");
                return "login/findIdPw"; // 비밀번호 재설정 이메일이 발송되었다는 메시지를 표시하는 뷰로 리다이렉트
            } catch (MessagingException e) {
                // 이메일 발송 중 오류가 발생한 경우 에러 메시지 표시
                model.addAttribute("error", "비밀번호 재설정 이메일 발송 중 오류가 발생했습니다.");
                return "login/findIdPw";
            }
        } else {
            // 사용자를 찾을 수 없는 경우 에러 메시지 표시
            model.addAttribute("error", "해당 이메일 주소를 가진 사용자를 찾을 수 없습니다.");
            return "login/findIdPw";
        }
    }
    
    @GetMapping("/changePw")
    public String showChangePasswordPage(@RequestParam("token") String token, Model model) {
        User user = userService.getUserByPasswordResetToken(token);
        if (user != null) {
        	model.addAttribute("username", user.getUsername());
            model.addAttribute("token", token);
            return "login/changePw"; // 비밀번호 재설정 페이지로 이동
        } else {
            // 토큰이 유효하지 않거나 만료된 경우
            model.addAttribute("error", "비밀번호 재설정 요청이 유효하지 않습니다.");
            return "error";
        }
    }

    // 비밀번호 변경 요청을 처리하는 메서드
    @PostMapping("/changePw")
    public String changeUserPassword(@RequestParam("token") String token,
                                     @RequestParam("password") String newPassword,
                                     Model model) {
        User user = userService.getUserByPasswordResetToken(token);
        if (user != null && newPassword != null) {
            userService.changeUserPassword(user, newPassword);
            model.addAttribute("message","비밀번호가 변경되었습니다.");
            return "login/message"; // 비밀번호 변경 성공 페이지로 이동
        } else {
            // 토큰이 유효하지 않거나 새 비밀번호가 제공되지 않은 경우
            model.addAttribute("error", "비밀번호 변경에 실패했습니다.");
            return "login/changePw"; // 비밀번호 재설정 페이지로 다시 이동
        }
    }
    
    @GetMapping("/message")
    public String showMessage(Model model) {
        model.addAttribute("message", "비밀번호를 바꾸었습니다. 새로운 비밀번호로 로그인하세요.");
        model.addAttribute("redirect", "/");
        return "login/message"; 
    }

    
    
    
   
    
}
    
