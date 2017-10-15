package com.neuqer.mail.exception.File;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-25.
 */
public class FileSizeLimitException extends BaseException {
    public FileSizeLimitException() {
        super.setCode(50003);
        super.setMessage("File size beyond limit");
    }
}
