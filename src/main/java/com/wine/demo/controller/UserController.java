package com.wine.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wine.demo.exception.UserNotFoundException;
import com.wine.demo.model.User;
import com.wine.demo.model.VerificationCode;
import com.wine.demo.service.UserService;

import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UserController {
	 
	 @Autowired
	 private UserDetailsService userDetailsService;

	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
     @Autowired
     private UserService userService;
    
     // 로그인페이지
     @GetMapping("/login")
     public String showLoginPage(HttpServletRequest request, HttpSession session) {
     	 String referrer = request.getHeader("Referer"); // 이전에 방문한 페이지의 url을 가져옴
    	    if (referrer != null && !referrer.contains("/login")) {
    	        session.setAttribute("prevPage", referrer); // 이전 페이지의 url을 세션에 저장
    	    } 
    	    return "login/login";
    	}

     // 로그인 요청을 처리 
     @PostMapping("/login")
     public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
         if (username == null || password == null) { 
             model.addAttribute("error", "사용자 이름 또는 비밀번호가 누락되었습니다."); //입력누락에 대한 간단한 검증만 수행하고 로그인시도및 실패처리는 handler가 담당
             return "login";
         }

         if (userService.loginUser(username, password)) {
             session.setAttribute("username", username); //로그인정보 세션에 저장
             String prevPage = (String) session.getAttribute("prevPage"); // 이전에 방문한 페이지로 리다이렉트(session의 prevpage에서 이전페이지정보를 가져옴)
             session.removeAttribute("prevPage"); //페이지url데이터 세션에서 삭제
             return "redirect:" + (prevPage != null ? prevPage : "/"); // 이전 페이지가 null이 아니면 해당페이지로 null이면 홈페이지로 리다이렉트
         } else {
             model.addAttribute("error", "잘못된 사용자 이름 또는 비밀번호입니다.");
             return "login";
         }
     }

    // 회원가입 페이지
    @GetMapping("/register")
    public String register() {
        return "login/register";
    }
    
    // id 중복확인
    @PostMapping("/checkUsername")
    @ResponseBody
    public ResponseEntity<Boolean> checkUsername(@RequestBody Map<String, String> payload) { //json형식
        String username = payload.get("username");
        boolean isAvailable = userService.checkUsernameAvailability(username);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
    
    // 인증이메일 전송처리
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
        return new ResponseEntity<>("이메일 전송에 성공했습니다. 이메일을 확인해주세요!", HttpStatus.OK);
    }
    
    // 회원가입 요청을 처리
    @PostMapping("/register")
    @ResponseBody   // HTTP 요청 본문의 내용을 자바 객체로 변환하여 매개변수에 바인딩. 이 경우에는 요청 본문은 Map<String, String> 형태로 변환
    public String processRegister(@RequestBody Map<String, String> payload) {

        String username = payload.get("username");
        String password = payload.get("password");
        String passwordConfirm = payload.get("passwordConfirm");
        String email = payload.get("email");
        String verificationCode = payload.get("verificationCode");

        if (!password.equals(passwordConfirm)) {
            return "register";
        }

        VerificationCode code = userService.findVerificationCode(verificationCode, email);

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
    
    // 아이디확인 및 비밀번호 찾기 페이지 
    @GetMapping("/findIdPw")
    public String showFindIdPwPage() {
        return "login/findIdPw";
    }
    
    // 아이디확인 및 비밀번호 재설정 요청을 처리하는 메서드
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
                model.addAttribute("message", "아이디찾기 및 비밀번호 재설정 이메일이 발송되었습니다.");
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
    
    //비밀번호 변경 페이지
    @GetMapping("/changePw")
    public String showChangePasswordPage(@RequestParam("token") String token, Model model) {
        User user = userService.getUserByPasswordResetToken(token);
        if (user != null) {
        	model.addAttribute("username", user.getUsername());
            model.addAttribute("token", token);
            return "login/changePw"; 
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
    
    //변경완료 메세지 페이지 출력
    @GetMapping("/message")
    public String showMessage(Model model) {
        model.addAttribute("message", "비밀번호 변경이 완료되었습니다. 새로운 비밀번호로 로그인하세요.");
        model.addAttribute("redirect", "/");
        return "login/message"; 
    }

    
    
    
}
    
