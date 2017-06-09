package com.neuqer.mail.service;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Template;

import java.util.List;

/**
 * Created by Hotown on 17/5/24.
 */
public interface TemplateService extends BaseService<Template, Long> {

    boolean isTemplateExist(Long templateId) throws BaseException;

    boolean saveTemplate(Template template) throws BaseException;

    Template getTemplateById(Long templateId) throws BaseException;

    List<Template> getTemplatesByUserId(Long userId) throws BaseException;

    boolean updateTemplate(Long templateId, String templateName, String templateContent) throws BaseException;

//    boolean updateTemplateName(Long templateId, String templateName) throws BaseException;
//
//    boolean updateTemplateContent(Long templateId, String templateContent) throws BaseException;

    boolean deleteTemplate(Long templateId, Long userId) throws BaseException;
}
