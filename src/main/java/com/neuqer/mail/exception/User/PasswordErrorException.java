package com.neuqer.mail.exception.User;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by Hotown on 17/5/18.
 */
public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {
        super.setCode(20002);
    }
}
