package com.neuqer.mail.service;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;

/**
 * Created by Hotown on 17/5/16.
 */
public interface TokenService extends BaseService<Token, Long> {
    Token getTokenByUserId(long userId);

    Token generateToken(long userId, int client);

    User verifyToken(String tokenStr) throws BaseException;
}
