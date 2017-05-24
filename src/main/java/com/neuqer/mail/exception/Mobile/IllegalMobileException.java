package com.neuqer.mail.exception.Mobile;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-18.
 */
public class IllegalMobileException extends BaseException {
    public IllegalMobileException() {
        super.setCode(30001);
        super.setMessage("Illegal mobile");
    }
}
