package com.neuqer.mail.exception.Client;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by Hotown on 17/6/6.
 */
public class ApiException extends BaseException {
    public ApiException (String msg) {
        super.setCode(80001);
        super.setMessage(msg);
    }
}
