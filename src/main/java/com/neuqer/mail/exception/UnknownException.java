package com.neuqer.mail.exception;


/**
 * Created by Hotown on 17/5/16.
 */
public class UnknownException extends BaseException {
    public UnknownException() {
        super.setCode(10000);
    }
    public UnknownException(String message) {
        super.setCode(10001);
        super.setMessage(message);
    }
}
