package com.rjAbhi.journalApp.service;

import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //  we want the logger have only one instance for this journalEntryService class
//    har ek logger ek specific class ke associated hota hai
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean SaveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
//            logger.info("Error occurred for {}:",user.getUsername(),e);
            log.error("Error occurred for {}:", user.getUsername(), e);
            log.info("Error occurred for {}:", user.getUsername(), e);
            log.debug("Error occurred for {}:", user.getUsername(), e);
            log.trace("Error occurred for {}:", user.getUsername(), e);
            log.warn("Error occurred for {}:", user.getUsername(), e);
            return false;
        }

    }

    public void SaveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }
}
