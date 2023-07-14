package com.wine.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.AuthenticationException;



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
    public String showLoginPage() {
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
                return "redirect:/";  // Redirect to loginSuccess view
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
    
//    @GetMapping("/newregister")
//    public String showLoginsuccessPage() {
//        return "newregister";
//    }
    
    
}