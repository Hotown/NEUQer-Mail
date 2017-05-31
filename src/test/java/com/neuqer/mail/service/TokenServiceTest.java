package com.neuqer.mail.service;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dgy on 17-5-24.
 */
public class TokenServiceTest extends BaseTest {
    @Autowired
    TokenService tokenService;
    @Test
    public void verifyToken()throws BaseException {
        String tokenStr = "177913e13a44444b9c4bfa97e4a3abbc";
        User user = tokenService.verifyToken(tokenStr);
        System.out.print(user);
    }
}
