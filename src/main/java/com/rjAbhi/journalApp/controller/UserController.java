package com.rjAbhi.journalApp.controller;


import com.rjAbhi.journalApp.api.response.WeatherApiResponse;
import com.rjAbhi.journalApp.entity.User;

import com.rjAbhi.journalApp.repository.UserRepository;
import com.rjAbhi.journalApp.service.UserService;
import com.rjAbhi.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUsername(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.SaveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherApiResponse weatherApiResponse= weatherService
                .getWeather("25.2050","85.5174");
        String greetings="";
        if(weatherApiResponse!=null)
        {
            greetings=" ,Weather feels like "+weatherApiResponse.getMain().getFeelsLike();
        }
        return new ResponseEntity<>(
                "Hi "+authentication.getName() +greetings,
                HttpStatus.OK);
    }

}
