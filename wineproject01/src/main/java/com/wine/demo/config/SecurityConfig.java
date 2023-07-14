package com.wine.demo.config;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	    @Autowired
	    private UserDetailsService userDetailsService;
		
	    @Autowired
	    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;
	    
	    @Bean
	    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
	        return new OAuth2AuthenticationSuccessHandler();
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
	
		@Bean
		public AuthenticationSuccessHandler successHandler() {
		    return new AuthenticationSuccessHandler() {
		        @Override
		        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		            httpServletRequest.getSession().setAttribute("username", authentication.getName());
		            httpServletResponse.sendRedirect("/");
		        }
		    };
		}
            
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/","/register", "/sendVerificationEmail","/checkUsername","/loginsuccess","/login","/login/**",
                                "/save","/findIdPw","/findUsername","/resetPassword","/changePassword","/verifyTempPassword","/community/**","/index" ,"/authenticate", "/login/oauth2/code/google").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
	            .loginPage("/login")
	            .successHandler(successHandler())
            .and()
            .oauth2Login()
                .loginPage("/login")
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .userInfoEndpoint()
                .oidcUserService(oidcUserService)
                .and()
                .failureUrl("/login?error=true")
	        .and()
	        .logout()
	            .logoutUrl("/logout")
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessHandler(new CustomLogoutSuccessHandler()) 
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID")
	            .clearAuthentication(true)
	            .permitAll();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**","/scss/**","/vendor/**","/fonts/**");
    }
   
}