package com.neuqer.mail.exception.Mobile;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-18.
 */
public class MobileExistException extends BaseException {
    public MobileExistException(String mobile){
        super.setCode(30002);
        super.setMessage(mobile+"exist in this group!");
    }
}
