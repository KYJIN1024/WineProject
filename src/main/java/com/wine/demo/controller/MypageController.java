package com.wine.demo.controller;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wine.demo.entity.CommentEntity;
import com.wine.demo.entity.FreeBoardEntity;
import com.wine.demo.entity.ProducerEntity;
import com.wine.demo.entity.ShopEntity;
import com.wine.demo.service.UserInfoService;
import com.wine.demo.service.UserService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MypageController {
	
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/mypage")
    public String showLoginsuccessPage(HttpSession session, Model model) {
    	
    	 String username = (String) session.getAttribute("username");
    	 int postCount = userInfoService.getUserPostCount(username);
    	 int commentCount = userInfoService.getUserCommentCount(username);
    	    
    	 model.addAttribute("username", username);
    	 model.addAttribute("postCount", postCount);
    	 model.addAttribute("commentCount", commentCount);
        return "login/mypage";
    }

    @GetMapping("/mypage-edit-modal")
    public String showEditPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        return "login/mypage-edit";
    }
    

    @PostMapping("/update-email")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateEmail(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");

        if (email == null || email.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "이메일이 비어있습니다.");
            return ResponseEntity.status(400).body(errorResponse);
        }

        try {
            userInfoService.updateEmail(email);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "이메일 업데이트 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/update-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody Map<String, String> requestData) {
        String newPassword = requestData.get("newPassword");

        if (newPassword == null || newPassword.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "비밀번호가 비어있습니다.");
            return ResponseEntity.status(400).body(errorResponse);
        }

        try {
            userInfoService.updatePassword(newPassword);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "비밀번호 업데이트 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    
    @GetMapping("/liked-producers")
    public String likedProducers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        List<ProducerEntity> producers = userService.getLikedProducers(username);
        model.addAttribute("producers", producers);
        return "login/liked-producers"; // 이는 해당 게시글 목록을 보여주는 뷰 이름입니다.
    }
    
    @GetMapping("/liked-shops")
    public String likedShops(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        List<ShopEntity> shops = userService.getLikedShops(username);
        model.addAttribute("shops", shops);
        return "login/liked-shops"; // 이는 해당 게시글 목록을 보여주는 뷰 이름입니다.
    }
    
    @GetMapping("/user-posts")
    @ResponseBody
    public List<FreeBoardEntity> getUserPosts(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return userInfoService.getUserPosts(username);
    }

    @GetMapping("/user-comments")
    @ResponseBody
    public List<CommentEntity> getUserComments(HttpSession session) {
    	 	String username = (String) session.getAttribute("username");
    	    List<CommentEntity> userComments = userInfoService.getUserComments(username);
    	    // 로그를 통해 댓글 데이터 확인
    	    System.out.println(userComments);
    	    return userComments;
    }
    

}