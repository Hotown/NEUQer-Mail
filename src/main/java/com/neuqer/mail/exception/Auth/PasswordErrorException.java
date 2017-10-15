package com.neuqer.mail.exception.Auth;

import com.neuqer.mail.exception.BaseException;

public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {
        super.setCode(20002);
        super.setMessage("Password Wrong");
    }
}
