package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repositery.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository ;
    @Disabled
    @BeforeEach
    void setUp(){
        //if you want to initialise something before each running of any test in this file
        //  and @BeforeAll for test before all the test
        //@AfterEach and @AfterAll for after
    }

    @Disabled
    @Test
    public void testFindByUserName(){
        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUserName("vipul"));
    }
    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,4,8"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
