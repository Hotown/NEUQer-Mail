package com.neuqer.mail.service.impl;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.Template.TemplateNotExistException;
import com.neuqer.mail.exception.Template.UserNotHasTemplate;
import com.neuqer.mail.mapper.TemplateMapper;
import com.neuqer.mail.model.Template;
import com.neuqer.mail.service.TemplateService;
import com.neuqer.mail.utils.Utils;
import org.apache.el.parser.BooleanNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hotown on 17/5/24.
 */
@Service("TemplateService")
public class TemplateServiceImpl extends BaseServiceImpl<Template, Long> implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public boolean isTemplateExist(Long templateId) throws BaseException {
        Template template = templateMapper.selectByPrimaryKey(templateId);

        if (template == null) {
            throw new TemplateNotExistException();
        }

        return true;
    }

    @Override
    public boolean saveTemplate(Template template) throws BaseException {
        Long currentTime = Utils.createTimeStamp();

        template.setCreatedAt(currentTime);
        template.setUpdatedAt(currentTime);

        return save(template) == 1;
    }

    @Override
    public Template getTemplateById(Long templateId) throws BaseException {
        Template template = selectByPrimaryKey(templateId);
        if (template == null) {
            throw new TemplateNotExistException();
        }
        return template;
    }

    @Override
    public List<Template> getTemplatesByUserId(Long userId) throws BaseException {
        return select(
                new Template() {
                    @Override
                    public void setUserId(Long userId) {
                        super.setUserId(userId);
                    }
                }
        );
    }

    @Override
    public boolean updateTemplateName(Long templateId, String templateName) throws BaseException {
        isTemplateExist(templateId);

        Template newTemplate = new Template();
        Long currentTime = Utils.createTimeStamp();

        newTemplate.setId(templateId);
        newTemplate.setTempName(templateName);
        newTemplate.setUpdatedAt(currentTime);

        return updateByPrimaryKeySelective(newTemplate) == 1;
    }

    @Override
    public boolean updateTemplateContent(Long templateId, String templateContent) throws BaseException {
        isTemplateExist(templateId);

        Template newTemplate = new Template();
        Long currentTime = Utils.createTimeStamp();

        newTemplate.setId(templateId);
        newTemplate.setContent(templateContent);
        newTemplate.setUpdatedAt(currentTime);

        return updateByPrimaryKeySelective(newTemplate) == 1;
    }

    @Override
    public boolean deleteTemplate(Long templateId, Long userId) throws BaseException {
        isTemplateExist(templateId);

        /**
         * 验证用户是否有这个模板
         */
        if (selectByPrimaryKey(templateId).getUserId() != userId) {
            throw new UserNotHasTemplate();
        }

        return deleteByPrimaryKey(templateId) == 1;
    }
}


