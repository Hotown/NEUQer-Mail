package com.neuqer.mail.exception.File;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-25.
 */
public class NullFileException extends BaseException{
    public NullFileException() {
        super.setCode(50001);
        super.setMessage("File is null");
    }
}
