package com.wine.demo.config;


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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable() 
	        .authorizeRequests()
	        .antMatchers("/","/register", "/sendVerificationEmail","/checkUsername","/loginsuccess",
	        					"/save","/findIdPw","/findUsername","/resetPassword","/changePassword","/verifyTempPassword","/community/**","/index" ,"/authenticate").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .formLogin()
	            .loginPage("/login")
	            .defaultSuccessUrl("/loginsuccess") // 로그인 성공 시 리다이렉트할 URL
	            .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
	            .permitAll()
	        .and()
	        .logout()
	            .permitAll();
	}
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**","/scss/**");
    }
   
}