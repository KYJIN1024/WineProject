package com.wine.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
	
	@Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
		
    @Test
    void testLoginUser_Success() {
        String username = "testUser";
        String password = "testPass";

        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(mockAuthentication);

        boolean result = userService.loginUser(username, password);
        assertTrue(result);
    }
    
    @Test
    void testLoginUser_Failure() {
        String username = "testUser";
        String password = "wrongPass";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Bad Credentials"));

        boolean result = userService.loginUser(username, password);
        assertFalse(result);
    }
    
    @Test
    void testLoginUser_UsernameNotFoundException() {
        String username = "nonExistingUser";
        String password = "testPass";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new UsernameNotFoundException("User not found"));

        boolean result = userService.loginUser(username, password);
        assertFalse(result);
    }
    

}