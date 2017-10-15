package com.neuqer.mail.service.impl;

import com.neuqer.mail.exception.Auth.NeedLoginException;
import com.neuqer.mail.exception.Auth.TokenExpiredException;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.mapper.TokenMapper;
import com.neuqer.mail.mapper.UserMapper;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TokenService;
import com.neuqer.mail.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


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
        Example example = new Example(Token.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return tokenMapper.selectByExample(example).get(0);
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
        Example example = new Example(Token.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("token", tokenStr);
        Token token = tokenMapper.selectByExample(example).get(0);

        Long currentTime = Utils.createTimeStamp();

        if (token == null) {
            throw new NeedLoginException();
        }

        if (token.getExpiredAt() < currentTime) {
            throw new TokenExpiredException();
        }

        Long userId = token.getUserId();
        Example userExample = new Example(User.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("userId", userId);
        User user = userMapper.selectByExample(userExample).get(0);
        return user;
    }

    @Override
    public Token getTokenByTokenStr(String tokenStr) throws BaseException {
        Example example = new Example(Token.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("token", tokenStr);
        Token token = tokenMapper.selectByExample(example).get(0);

        return token != null ? token : null;
    }
}
