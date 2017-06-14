package com.neuqer.mail.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuqer.mail.common.ExcelCommon;
import com.neuqer.mail.common.Response;
import com.neuqer.mail.dto.request.TemplateCreateRequest;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.File.NullFileException;
import com.neuqer.mail.exception.UnknownException;
import com.neuqer.mail.model.BaseModel;
import com.neuqer.mail.model.Template;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TemplateService;
import com.neuqer.mail.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Hotown on 17/5/25.
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    private Logger logger = LoggerFactory.getLogger(TemplateController.class);

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
     * 编辑模板
     *
     * @param templateId
     * @param request
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/{tid}/info", method = RequestMethod.PUT)
    public Response updateTemplate(@PathVariable("tid") Long templateId,
                                   @RequestBody JSONObject request) throws BaseException {
        if (!templateService.updateTemplate(templateId, request.getString("tempName"), request.getString("content"))) {
            throw new UnknownException("Update template error.");
        }

        return new Response(0);
    }

//    /**
//     * 修改模板名称
//     *
//     * @param tempId
//     * @param request
//     * @return
//     * @throws BaseException
//     */
//    @ResponseBody
//    @RequestMapping(path = "/{tid}/name", method = RequestMethod.PUT)
//    public Response updateTemplateName(@PathVariable("tid") Long tempId,
//                                       @RequestBody JSONObject request) throws BaseException {
//        if (!templateService.updateTemplateName(tempId, request.getString("tempName"))) {
//            throw new UnknownException("Update tempName error.");
//        }
//
//        return new Response(0);
//    }
//
//    /**
//     * 修改模板内容
//     *
//     * @param tempId
//     * @param request
//     * @return
//     * @throws BaseException
//     */
//    @ResponseBody
//    @RequestMapping(path = "/{tid}/content", method = RequestMethod.PUT)
//    public Response updateTemplateContent(@PathVariable("tid") Long tempId,
//                                          @RequestBody JSONObject request) throws BaseException {
//        if (!templateService.updateTemplateContent(tempId, request.getString("content"))) {
//            throw new UnknownException("Update tempContent error.");
//        }
//
//        return new Response(0);
//    }

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

    /**
     * 根据模板内容发送发送
     *
     * @param tempId
     * @param file
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/{tid}/send", method = RequestMethod.POST)
    public Response sendWithTemplate(@PathVariable("tid") Long tempId,
                                     @RequestParam("file") MultipartFile file) throws BaseException, IOException {
        if (file.isEmpty()) {
            throw new NullFileException();
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传文件名为：" + fileName);

        // 获取文件的后缀名
        String postfix = fileName.substring(fileName.lastIndexOf(ExcelCommon.POINT));
        logger.info("上传文件后缀名为：" + postfix);

        // 文件上传路径
        String filePath = "/Users/Hotown/WorkSpace/java_project/neuqer-mail-system/src/main/resources/upload/";

        fileName = "tid" + tempId.toString() + "-" + Utils.createTimeStamp() + postfix;

        File dest = new File(filePath + fileName);

        // 检测目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        // 将文件存储在本地
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!templateService.sendWithTemplate(tempId, dest.getPath())) {
            throw new UnknownException("Somthing wrong with sending system.");
        }

        return new Response(0);
    }
}
