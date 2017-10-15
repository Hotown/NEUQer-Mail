package com.neuqer.mail.service.impl;

import com.neuqer.mail.dto.request.RegisterRequest;
import com.neuqer.mail.dto.response.LoginResponse;
import com.neuqer.mail.exception.Auth.PasswordErrorException;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.mapper.UserMapper;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TokenService;
import com.neuqer.mail.service.UserService;
import com.neuqer.mail.utils.EncryptionUtil;
import com.neuqer.mail.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long>
                                implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public User register(RegisterRequest register) {
        User user = new User();
        user.setMobile(register.getMobile());
        user.setPassword(EncryptionUtil.getHash(register.getPassword(), "MD5"));
        user.setCreatedAt(Utils.createTimeStamp());
        user.setUpdatedAt(Utils.createTimeStamp());

        if (userMapper.insert(user) == 1) {
            user.setPassword("*********");
            return user;
        }
        return null;
    }

    @Override
    public LoginResponse login(String mobile, String password, int client) throws BaseException {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", mobile);
        User user = userMapper.selectByExample(example).get(0);

        if (user.getPassword() != EncryptionUtil.getHash(password, "MD5")
                || !user.getPassword().equals(EncryptionUtil.getHash(password, "MD5"))){
            throw new PasswordErrorException();
        }

        Token token = tokenService.generateToken(user.getId(), client);

        return new LoginResponse(user, token);
    }
}
