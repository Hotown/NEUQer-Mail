package com.neuqer.mail.exception.Template;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by Hotown on 17/5/31.
 */
public class UserNotHasTemplate extends BaseException {
    public UserNotHasTemplate() {
        super.setCode(70002);
    }
}
