package com.neuqer.mail.service;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;

public interface TokenService extends BaseService<Token, Long> {
    Token getTokenByUserId(long userId);

    Token generateToken(long userId, int client);

    User verifyToken(String tokenStr) throws BaseException;

    Token getTokenByTokenStr(String tokenStr) throws BaseException;
}
