package com.neuqer.mail.exception.Template;

import com.neuqer.mail.exception.BaseException;

/**
 * Created by Hotown on 17/5/25.
 */
public class TemplateNotExistException extends BaseException {
    public TemplateNotExistException() {
        super.setCode(70001);
    }
}
