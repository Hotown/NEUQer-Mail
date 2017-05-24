package com.neuqer.mail.exception.Group;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-19.
 */
public class NotCreatorException extends BaseException {
    public NotCreatorException(){
        super.setCode(40002);
        super.setMessage("该群组不属于当前用户！");
    }
}
