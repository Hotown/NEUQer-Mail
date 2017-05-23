package com.neuqer.mail.exception;

/**
 * Created by Hotown on 17/5/22.
 */
public class BadRequestException extends BaseException {
    public BadRequestException() {
        super.setCode(10002);
    }
}
