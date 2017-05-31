package com.neuqer.mail.exception.Mobile;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-23.
 */
public class MobileNotExistException extends BaseException {
    public MobileNotExistException() {
        super.setCode(60003);
        super.setMessage("mobile not exist!");
    }
}
