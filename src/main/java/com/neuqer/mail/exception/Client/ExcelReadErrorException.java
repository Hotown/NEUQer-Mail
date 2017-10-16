package com.neuqer.mail.exception.Client;

import com.neuqer.mail.exception.BaseException;

public class ExcelReadErrorException extends BaseException {
    public ExcelReadErrorException() {
        super.setCode(80002);
        super.setMessage("Fail to read excel");
    }
}
