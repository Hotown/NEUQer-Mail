package com.neuqer.mail.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuqer.mail.common.Response;
import com.neuqer.mail.dto.request.TemplateCreateRequest;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.UnknownException;
import com.neuqer.mail.model.BaseModel;
import com.neuqer.mail.model.Template;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TemplateService;
import com.neuqer.mail.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hotown on 17/5/25.
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 获取单个模板信息
     *
     * @param tempId
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/{tid}/info", method = RequestMethod.GET)
    public Response getTemplateById(@PathVariable("tid") Long tempId) throws BaseException {
        Template template = templateService.getTemplateById(tempId);

        HashMap<String, BaseModel> data = new HashMap<String, BaseModel>() {{
            put("template", template);
        }};

        return new Response(0, data);
    }

    /**
     * 获取用户所有模板信息
     *
     * @param request
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/infos", method = RequestMethod.GET)
    public Response getTemplatesByUserId(HttpServletRequest request) throws BaseException {
        User user = (User) request.getAttribute("user");
        List<Template> templates = templateService.getTemplatesByUserId(user.getId());

        HashMap<String, List> data = new HashMap<String, List>() {{
            put("templates", templates);
        }};

        return new Response(0, data);
    }

    /**
     * 创建模板
     *
     * @param request
     * @param httpServletRequest
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public Response createTemplate(@RequestBody @Valid TemplateCreateRequest request,
                                   HttpServletRequest httpServletRequest) throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        Template template = new Template();
        Long currentTime = Utils.createTimeStamp();

        template.setTempName(request.getTempName());
        template.setContent(request.getContent());
        template.setUserId(user.getId());
        template.setCreatedAt(currentTime);
        template.setUpdatedAt(currentTime);

        if (!templateService.saveTemplate(template)) {
            throw new UnknownException("Create template error.");
        }

        HashMap<String, BaseModel> data = new HashMap<String, BaseModel>() {{
            put("template", template);
        }};

        return new Response(0, data);
    }


    /**
     * 修改模板名称
     *
     * @param tempId
     * @param request
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/{tid}/name", method = RequestMethod.PUT)
    public Response updateTemplateName(@PathVariable("tid") Long tempId,
                                       @RequestBody JSONObject request) throws BaseException {
        if (!templateService.updateTemplateName(tempId, request.getString("tempName"))) {
            throw new UnknownException("Update tempName error.");
        }

        return new Response(0);
    }

    /**
     * 修改模板内容
     *
     * @param tempId
     * @param request
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/{tid}/content", method = RequestMethod.PUT)
    public Response updateTemplateContent(@PathVariable("tid") Long tempId,
                                          @RequestBody JSONObject request) throws BaseException {
        if (!templateService.updateTemplateContent(tempId, request.getString("content"))) {
            throw new UnknownException("Update tempContent error.");
        }

        return new Response(0);
    }

    /**
     * 删除模板
     *
     * @param tempId
     * @param request
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/{tid}/delete", method = RequestMethod.DELETE)
    public Response deleteTemplate(@PathVariable("tid") Long tempId, HttpServletRequest request) throws BaseException {
        User user = (User) request.getAttribute("user");

        if (!templateService.deleteTemplate(tempId, user.getId())) {
            throw new UnknownException();
        }

        return new Response(0);
    }
}
