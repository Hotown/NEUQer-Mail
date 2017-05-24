package com.neuqer.mail.service;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Hotown on 17/5/24.
 */
public class TokenServiceTest extends BaseTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void verifyToken() throws BaseException {
        String token = "b2e2029c039348a2957eb90fec989a4a";
        User user = tokenService.verifyToken(token);
    }
}
