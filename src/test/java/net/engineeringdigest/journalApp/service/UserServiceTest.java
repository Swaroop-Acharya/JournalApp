package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testAdd(){
        assertEquals(4,2+2-4+4);
    }

    @Test
     public  void testFindUser(){
        assertNotNull(userRepository.findByUserName("vikas"));
    }


    @Disabled
    @Test
    public void testTrue(){
        User user = userRepository.findByUserName("Swaroop");
        assertFalse(user.getJournalEntries().isEmpty());
    }


    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "1,3,2",
            "10,3,2",
            "1,3,4",
    })

    public void test(int a ,int b ,int expected){
        assertEquals(expected,a+b);
    }


    @ParameterizedTest
    @ValueSource(strings={"Swaroop","Appa","Sudeep","Karthik"})
    public void testUsersExist(String userName){
        assertNotNull(userRepository.findByUserName(userName),"Failed for :"+userName);
    }
}
