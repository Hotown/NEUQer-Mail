package com.neuqer.mail.service;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.exception.BaseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Hotown on 17/5/16.
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void registerTest() throws BaseException{
        String mobile = "15032321389";
        String password = "neuqer2016";
        userService.registerUser(mobile, password);

        System.out.println("Success");

    }
    @Test
    public void selectTest(){
        Long userId = new Long(5);
        User user = userService.selectByPrimaryKey(userId);
        System.out.print(user);
    }
}
