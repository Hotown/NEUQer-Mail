package com.neuqer.mail.service;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Template;

/**
 * Created by Hotown on 17/5/24.
 */
public interface TemplateService extends BaseService<Template, Long> {

    boolean saveTemplate(Template template) throws BaseException;

}
