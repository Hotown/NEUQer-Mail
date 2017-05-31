package com.neuqer.mail.exception.File;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.neuqer.mail.exception.BaseException;

/**
 * Created by dgy on 17-5-25.
 */
public class ErrorFileTypeException extends BaseException {
    public ErrorFileTypeException() {
        super.setCode(50002);
        super.setMessage("文件类型错误");
    }
}
