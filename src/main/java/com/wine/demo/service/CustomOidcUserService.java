package com.wine.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.wine.demo.model.User;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String googleUsername = oidcUser.getSubject();  // Google의 유저 ID를 가져옵니다.
        User user = userService.findByUsername(googleUsername);  // 데이터베이스에서 유저를 찾습니다.

        if (user == null) {  // 만약 유저가 데이터베이스에 존재하지 않는다면
            user = new User();  // 새로운 유저를 생성하고
            user.setUsername(googleUsername);  // 유저 정보를 설정한 후
            userService.save(user, false);  // 데이터베이스에 저장합니다.
        }

        return oidcUser;
    }

}
