package com.wine.demo.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public CustomAuthenticationSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 세션에 사용자 이름 설정
        request.getSession().setAttribute("username", authentication.getName());

        // 세션에서 이전 페이지 URL 가져오기
        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            request.getSession().removeAttribute("prevPage"); // 사용 후 세션에서 삭제
        }

        // 이전 페이지가 없는 경우 홈페이지로 리디렉션, 있으면 해당 페이지로 리디렉션
        response.sendRedirect(prevPage != null ? prevPage : getDefaultTargetUrl());
    }
}