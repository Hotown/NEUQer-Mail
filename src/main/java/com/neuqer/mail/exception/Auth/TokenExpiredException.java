package com.neuqer.mail.exception.Auth;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by Hotown on 17/5/16.
 */
public class TokenExpiredException extends BaseException {
    public TokenExpiredException() {
        super.setCode(20002);
    }
}
