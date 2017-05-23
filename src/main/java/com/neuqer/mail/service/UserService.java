package com.neuqer.mail.service;

import com.neuqer.mail.dto.request.LoginRequest;
import com.neuqer.mail.dto.response.LoginResponse;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.User;
import com.sun.xml.internal.rngom.parse.host.Base;

/**
 * Created by Hotown on 17/5/15.
 */
public interface UserService extends BaseService<User, Long> {
    LoginResponse registerUser(String mobile, String password) throws BaseException;

    LoginResponse login(String mobile, String password) throws BaseException;

    boolean updateNickname(long userId, String nickname) throws BaseException;

    boolean updatePassword(long userId, String password) throws BaseException;
}
