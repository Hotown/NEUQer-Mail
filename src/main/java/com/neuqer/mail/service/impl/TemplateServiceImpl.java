package com.neuqer.mail.service.impl;

import com.neuqer.mail.client.ClientFactory;
import com.neuqer.mail.client.excel.ExcelClient;
import com.neuqer.mail.client.sms.SMSClient;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.Template.TemplateNotExistException;
import com.neuqer.mail.exception.Template.UserNotHasTemplate;
import com.neuqer.mail.mapper.TemplateMapper;
import com.neuqer.mail.model.Template;
import com.neuqer.mail.service.TemplateService;
import com.neuqer.mail.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedList;
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
        Template template = new Template();
        template.setUserId(userId);
        return select(template);
    }

    @Override
    public boolean updateTemplate(Long templateId, String templateName, String templateContent) throws BaseException {
        isTemplateExist(templateId);

        Template newTemplate = new Template();
        Long currentTime = Utils.createTimeStamp();

        newTemplate.setId(templateId);

        if (templateName != null) {
            newTemplate.setTempName(templateName);
        }

        if (templateContent != null) {
            newTemplate.setContent(templateContent);
        }

        newTemplate.setUpdatedAt(currentTime);

        return updateByPrimaryKeySelective(newTemplate) == 1;
    }

    //    @Override
//    public boolean updateTemplateName(Long templateId, String templateName) throws BaseException {
//        isTemplateExist(templateId);
//
//        Template newTemplate = new Template();
//        Long currentTime = Utils.createTimeStamp();
//
//        newTemplate.setId(templateId);
//        newTemplate.setTempName(templateName);
//        newTemplate.setUpdatedAt(currentTime);
//
//        return updateByPrimaryKeySelective(newTemplate) == 1;
//    }
//
//    @Override
//    public boolean updateTemplateContent(Long templateId, String templateContent) throws BaseException {
//        isTemplateExist(templateId);
//
//        Template newTemplate = new Template();
//        Long currentTime = Utils.createTimeStamp();
//
//        newTemplate.setId(templateId);
//        newTemplate.setContent(templateContent);
//        newTemplate.setUpdatedAt(currentTime);
//
//        return updateByPrimaryKeySelective(newTemplate) == 1;
//    }

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

    @Transactional
    @Override
    public boolean sendWithTemplate(Long templateId, String filePath) throws BaseException, IOException {
        // 创建SMS客户端
        SMSClient client = (SMSClient) ClientFactory.getClient("SMS");
        // 创建Excel解析器
        ExcelClient reader = new ExcelClient();
        // results接收excel内容
        LinkedList[] results = reader.readExcel(filePath);

        String originMessage = templateMapper.selectByPrimaryKey(templateId).getContent();

        for (int i = 1; i < results.length; i++) {
            String message = originMessage;
            for (int j = 0; j < results[i].size() - 1; j++) {
                String regex = Integer.toString(j);
                message = message.replaceAll("\\{" + regex + "\\}", results[i].get(j).toString());
            }
            String mobile = results[i].get(results[i].size() - 1).toString();
            System.out.println(client.accountPswdMobileMsgGet(mobile, message));
        }

        return true;
    }
}


