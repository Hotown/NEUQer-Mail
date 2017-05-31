package com.neuqer.mail.service.impl;

import com.neuqer.mail.exception.Auth.NeedLoginException;
import com.neuqer.mail.exception.Auth.TokenExpiredException;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.mapper.TokenMapper;
import com.neuqer.mail.mapper.UserMapper;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TokenService;
import com.neuqer.mail.service.UserService;
import com.neuqer.mail.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Hotown on 17/5/16.
 */
@Service("TokenService")
public class TokenServiceImpl extends BaseServiceImpl<Token, Long> implements TokenService {

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private UserMapper userMapper;

    private final static long EXPIRE_TIME = 3600000;

    @Override
    public Token getTokenByUserId(long userId) {
        return tokenMapper.getTokenByUserId(userId);
    }

    @Override
    public Token generateToken(long userId, int client) {
        Token token = getTokenByUserId(userId);

        long currentTime = Utils.createTimeStamp();

        if (token == null) {
            token = new Token();
            token.setUserId(userId);
            token.setToken(Utils.createUUID());
            token.setClient(client);
            token.setCreatedAt(currentTime);
            token.setUpdatedAt(currentTime);
            token.setExpiredAt(currentTime + EXPIRE_TIME);

            save(token);
        } else {
            token.setToken(Utils.createUUID());
            token.setUpdatedAt(currentTime);
            token.setExpiredAt(currentTime + EXPIRE_TIME);
            token.setClient(client);

            updateByPrimaryKey(token);
        }
        return token;
    }

    @Override
    public User verifyToken(String tokenStr) throws BaseException {
        Token token = tokenMapper.getTokenByTokenStr(tokenStr);
        Long currentTime = Utils.createTimeStamp();

        if (token == null) {
            throw new NeedLoginException();
        }

        if (token.getExpiredAt() < currentTime) {
            throw new TokenExpiredException();
        }

        Long userId = token.getUserId();
        User user = userMapper.getUserById(userId);
        return user;
    }
}
