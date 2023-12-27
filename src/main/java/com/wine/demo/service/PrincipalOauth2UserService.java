package com.wine.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.wine.demo.model.User;
import com.wine.demo.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println("getAttributes : " + oauth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId(); // Google
        String providerId = oauth2User.getAttribute("sub"); // Google ProviderId Sub
        String shortenedProviderId = providerId.length() > 5 ? providerId.substring(0, 5) : providerId; // providerId에서 길이 5만큼의 문자열을 추출
        String username = provider+"_"+shortenedProviderId; // google_123124
        String password = "1234"; // 크게 의미 없음.
        String email = oauth2User.getAttribute("email");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }
}