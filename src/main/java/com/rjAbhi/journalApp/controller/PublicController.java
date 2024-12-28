package com.rjAbhi.journalApp.controller;

import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.service.UserDetailsServiceImpl;
import com.rjAbhi.journalApp.service.UserService;
import com.rjAbhi.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;



    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody User user) {
        userService.SaveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getUsername(),user.getPassword()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getUsername());
            String jwtToken=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while create Authentication Token",e);
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }

    }

}
