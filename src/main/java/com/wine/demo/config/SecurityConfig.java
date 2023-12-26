package com.wine.demo.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.wine.demo.handler.CustomLogoutSuccessHandler;
import com.wine.demo.handler.OAuth2AuthenticationSuccessHandler;
import com.wine.demo.handler.UserLoginFailHandler;
import com.wine.demo.service.PrincipalOauth2UserService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import com.wine.demo.config.SecurityConstants;
import com.wine.demo.handler.CustomAuthenticationSuccessHandler;

	
	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled = true) 
	public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Autowired
	 private UserDetailsService userDetailsService;
	 
	 @Bean
	 public AuthenticationSuccessHandler successHandler() {
	     return new CustomAuthenticationSuccessHandler(
	         SecurityConstants.DEFAULT_SUCCESS_URL
	     );
	 }
	 
	 @Bean
	 public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
	     return new CustomAuthenticationSuccessHandler(SecurityConstants.DEFAULT_SUCCESS_URL);
	 }
	
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 @Override
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	     return super.authenticationManagerBean();
	 }
	
	 @Bean
	 public DaoAuthenticationProvider authenticationProvider() {
	     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     authProvider.setUserDetailsService(userDetailsService);
	     authProvider.setPasswordEncoder(passwordEncoder());
	     return authProvider;
	 }
	
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	     auth.authenticationProvider(authenticationProvider());
	 }
	 
	 @Autowired
	 @Lazy
	 private UserLoginFailHandler userLoginFailHandler;
	 
	 @Autowired
	 private PrincipalOauth2UserService principalOauth2UserService;
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	     http.csrf().disable()
	         .authorizeRequests()
	         .antMatchers("/community/freeboard/write").authenticated()
	         .antMatchers("/partners/producer/write","/partners/shop/write","/partners/job/write").authenticated()
	         .antMatchers("/","/register", "/sendVerificationEmail","/checkUsername","/loginsuccess","/login","/login/**","/mypage/**","/mypage-edit","/mypage-edit/**",
	                 "/save","/findIdPw","/findUsername","/resetPassword","/changePassword","/verifyTempPassword","/community/**","/index" ,"/authenticate", "/login/oauth2/code/google",
	                 "/checkCurrentPassword","/search","/changePw","/resetPassword","/search/**","/partners/**").permitAll()
	         .anyRequest().authenticated()
	         .and()
	         .formLogin()
	             .loginPage("/login")
	             .successHandler(customAuthenticationSuccessHandler())
	             .failureHandler(userLoginFailHandler)
	         .and()
	         .logout()
	             .logoutUrl("/logout")
	             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	             .logoutSuccessHandler(customLogoutSuccessHandler())
	             .invalidateHttpSession(true)
	             .deleteCookies("JSESSIONID")
	             .clearAuthentication(true)
	             .permitAll()
	         .and()
	         .oauth2Login()
             .loginPage("/login")
             .userInfoEndpoint()
             .userService(principalOauth2UserService);
	 }
	
	 @Override
	 public void configure(WebSecurity web) throws Exception {
	     web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**","/scss/**","/vendor/**","/fonts/**");
	 }
	 

	 @Bean
	 public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
	     return new CustomLogoutSuccessHandler();
	 }
	 
	 @Bean
	    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
	        return new OAuth2AuthenticationSuccessHandler();
	    }
	 
	 @Bean
	 public UserLoginFailHandler userLoginFailHandler() {
	     return new UserLoginFailHandler();
	 }
	 
	 
	}
    
	
    
   
