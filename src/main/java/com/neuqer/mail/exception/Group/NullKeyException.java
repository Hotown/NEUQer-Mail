package com.neuqer.mail.exception.Group;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-26.
 */
public class NullKeyException extends BaseException {
    public NullKeyException(String keyName) {
        super.setCode(40003);
        super.setMessage( keyName + " is null");
    }
}
