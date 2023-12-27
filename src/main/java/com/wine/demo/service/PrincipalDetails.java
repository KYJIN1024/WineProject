package com.wine.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.wine.demo.model.User;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // 컴포지션
    private Map<String,Object> attributes;

    // 일반 로그인 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    // OAuth 로그인 생성자
    public PrincipalDetails(User user,Map<String,Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 해당 User의 권한을 리턴하는곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }
    
    // 소셜 로그인 여부를 확인하는 메소드
    public boolean isSocial() {
        return attributes != null && !attributes.isEmpty();
    }
    


    // User 의 password 리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
