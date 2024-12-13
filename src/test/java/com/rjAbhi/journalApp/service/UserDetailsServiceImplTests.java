package com.rjAbhi.journalApp.service;

import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository
                .findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(User
                        .builder()
                        .username("Abhinav")
                        .password("Abhinav")
                        .roles(new ArrayList<>())
                        .build());
        UserDetails user=userDetailsService.loadUserByUsername("Abhinav");
        Assertions.assertNotNull(user);
    }
}
