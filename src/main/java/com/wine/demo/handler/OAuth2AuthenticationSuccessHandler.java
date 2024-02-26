package com.wine.demo.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	 //private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication; //oauth2토큰으로 객체르르 캐스팅
        String username = token.getPrincipal().getAttribute("name");  // 토큰에서 id추출
        request.getSession().setAttribute("username", username); // 세션에 사용자 이름 저장
      
        //이전에 방문한 페이지 URL을 세션에서 가져옴
        String redirectUrl = (String) request.getSession().getAttribute("prevPage");
        request.getSession().removeAttribute("prevPage");
        
        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            response.sendRedirect(redirectUrl); // 이전 페이지로 리다이렉트
        } else {
            response.sendRedirect("/"); // 이전 페이지 정보가 없으면 홈페이지로 리다이렉트
        }
    }
}