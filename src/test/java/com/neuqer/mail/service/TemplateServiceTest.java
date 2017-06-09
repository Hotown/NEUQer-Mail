package com.neuqer.mail.service;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Template;
import com.neuqer.mail.utils.Utils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Hotown on 17/5/25.
 */
public class TemplateServiceTest extends BaseTest {

    @Autowired
    private TemplateService templateService;

    @Test
    public void createTemp() throws BaseException {
        Template template = new Template();
        template.setUserId(1L);
        template.setTempName("test");
        template.setContent("this is a test");
        Long currentTime = Utils.createTimeStamp();
        template.setCreatedAt(currentTime);
        template.setUpdatedAt(currentTime);

        templateService.saveTemplate(template);
    }

    @Test
    public void updateTemplate() throws BaseException {
        String tName = "TemplateTest";
        Long tId = 7L;

        templateService.updateTemplate(tId, tName,null);
    }

    @Test
    public void getTempById() throws BaseException {
        Long tId = 1L;
        System.out.println(templateService.getTemplateById(tId));
    }

    @Test
    public void getTempByUserId() throws BaseException {
        Long userId = 1L;
        System.out.println(templateService.getTemplatesByUserId(userId));
    }
}
