package com.neuqer.mail.exception.User;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by Hotown on 17/5/22.
 */
public class MobileExistedException extends BaseException {
    public MobileExistedException() {
        super.setCode(20003);
    }
}
