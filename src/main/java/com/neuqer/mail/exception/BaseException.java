package com.neuqer.mail.exception;

/**
 * Created by Hotown on 17/5/16.
 */
public class BaseException extends Exception{
    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
