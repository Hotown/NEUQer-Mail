package com.neuqer.mail.service;

import com.neuqer.mail.dto.request.RegisterRequest;
import com.neuqer.mail.dto.response.LoginResponse;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.User;

public interface UserService extends BaseService<User, Long> {
    User register(RegisterRequest register);

    LoginResponse login(String mobile, String password, int client) throws BaseException;
}
