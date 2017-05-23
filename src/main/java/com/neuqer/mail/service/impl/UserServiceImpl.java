package com.neuqer.mail.service.impl;

import com.neuqer.mail.dto.response.LoginResponse;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.User.MobileExistedException;
import com.neuqer.mail.exception.User.PasswordErrorException;
import com.neuqer.mail.exception.User.UserNotExsitedException;
import com.neuqer.mail.mapper.UserMapper;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TokenService;
import com.neuqer.mail.service.UserService;
import com.neuqer.mail.utils.EncryptionUtil;
import com.neuqer.mail.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Hotown on 17/5/15.
 */
@Service("UserService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public LoginResponse registerUser(String mobile, String password) throws BaseException {
        User user = userMapper.getUserByMobile(mobile);
        String pwd = EncryptionUtil.getHash(password, "MD5");

        if (user != null) {
            throw new MobileExistedException();
        } else {
            user = new User();
            user.setMobile(mobile);
            user.setPassword(pwd);

            long currentTime = Utils.createTimeStamp();
            user.setCreatedAt(currentTime);
            user.setUpdatedAt(currentTime);
            save(user);
        }
        return login(mobile, password);
    }

    @Override
    public LoginResponse login(String mobile, String password) throws BaseException {
        User user = userMapper.getUserByMobile(mobile);

        if (user == null) {
            throw new UserNotExsitedException();
        }

        // 密码验证
        String pwd = EncryptionUtil.getHash(password, "MD5");
        if (!pwd.equals(user.getPassword())) {
            throw new PasswordErrorException();
        }

        long userId = user.getId();

        // client：1表示WEB端
        Token token = tokenService.generateToken(userId, 1);

        user.setPassword(null);

        return new LoginResponse(user, token);
    }

    @Override
    public boolean updateNickname(long userId, String nickname) throws BaseException {
        int col = userMapper.updateNickname(userId, nickname);

        return col > 0;
    }

    @Override
    public boolean updatePassword(long userId, String password) throws BaseException {
        // 密码加密
        String pwd = EncryptionUtil.getHash(password, "MD5");

        int col = userMapper.updatePassword(userId, pwd);

        return col > 0;
    }

}
