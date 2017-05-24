package com.neuqer.mail.service.impl;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.mapper.TemplateMapper;
import com.neuqer.mail.model.Template;
import com.neuqer.mail.service.TemplateService;
import com.neuqer.mail.utils.Utils;
import org.apache.el.parser.BooleanNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Hotown on 17/5/24.
 */
@Service("TemplateService")
public class TemplateServiceImpl extends BaseServiceImpl<Template, Long> implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public boolean saveTemplate(Template template) throws BaseException {
        Long currentTime = Utils.createTimeStamp();
        template.setCreatedAt(currentTime);
        template.setUpdatedAt(currentTime);
        return save(template) == 1;
    }


}


