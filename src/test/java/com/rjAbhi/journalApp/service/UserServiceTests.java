package com.rjAbhi.journalApp.service;

import com.rjAbhi.journalApp.entity.User;
import com.rjAbhi.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {


    //    for disabled any function to testing
    @Disabled
    @Test
    public void testAdd() {
        assertEquals(4, 2 + 2);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup(){
//        ye each test function se pahle chalega
    }

    @BeforeAll
    static void setRes(){

    }

    @AfterEach
    void finished(){

    }

    @Test
    public void testFindByUsername() {
//        Abhinav is there in database
        assertNotNull(userRepository.findByUsername("Abhinav"));
//        Ram is not present in database.
        assertNull(userRepository.findByUsername("Ram"));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        assertTrue(userService.SaveNewUser(user));

    }


    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,4,6",
            "3,4,7",
            "2,1,3"
    })
    public void testSum(int a, int b, int res) {
        assertEquals(res, a + b);
    }

}
