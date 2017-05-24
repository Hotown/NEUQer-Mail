package com.neuqer.mail.exception.Group;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-19.
 */
public class GroupNotExistException extends BaseException {
    public GroupNotExistException() {
        super.setCode(40001);
        super.setMessage("group not exist!");
    }
}
