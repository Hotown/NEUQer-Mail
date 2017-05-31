package com.neuqer.mail.controller;


import com.alibaba.fastjson.JSONObject;
import com.neuqer.mail.common.Response;
import com.neuqer.mail.domain.ExcelInfo;
import com.neuqer.mail.dto.request.AddMultipleRequest;
import com.neuqer.mail.domain.MobileRemark;
import com.neuqer.mail.dto.response.AddMultipleResponse;
import com.neuqer.mail.dto.response.GroupInfoResponse;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.File.ErrorFileTypeException;
import com.neuqer.mail.exception.File.FileSizeLimitException;
import com.neuqer.mail.exception.File.NullFileException;
import com.neuqer.mail.exception.Group.NullKeyException;
import com.neuqer.mail.exception.UnknownException;
import com.neuqer.mail.model.Group;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.GroupService;
import com.neuqer.mail.service.ToolService;
import com.neuqer.mail.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dgy on 17-5-17.
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    ToolService toolService;

    @RequestMapping("/test")
    public String testMethod() {
        return "Test success";
    }

    public static final ArrayList<String> UPLOAD_FILE_TYPE = new ArrayList<String>() {{
        add(".xlsx");
        add(".xls");
    }};
    private static final long UPLOAD_FILE_MAXSIZE = 5*1024*1024;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response createGroup(HttpServletRequest httpServletRequest, @RequestBody JSONObject jsonRequest) throws
            BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String groupName = jsonRequest.getString("groupName");
        if(groupName == null){
            throw new NullKeyException("groupName");
        }
        groupService.createGroup(groupName, user.getId());
        return new Response(0);
    }


    @Transactional
    @RequestMapping(value = "/{groupId}/delete", method = RequestMethod.DELETE)
    public Response deleteGroup(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest)
            throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        toolService.validateCreator(user.getId(), groupId);
        groupService.deleteGroup(groupId);

        return new Response(0);
    }


    @RequestMapping(value = "/{groupId}/name", method = RequestMethod.PUT)
    public Response updateGroupName(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest,
                                    @RequestBody JSONObject jsonObject) throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String newName = jsonObject.getString("newName");

        Group group = toolService.validateCreator(user.getId(), groupId);
        groupService.updateGroupName(group, newName);

        return new Response(0);
    }


    @Transactional
    @RequestMapping(value = "/{groupId}/mobile/addsingle", method = RequestMethod.POST)
    public Response addSingleMobile(@PathVariable("groupId") Long groupId, @RequestBody JSONObject jsonRequest,
                                    HttpServletRequest httpServletRequest)
            throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String mobile = jsonRequest.getString("mobile");
        String remark = jsonRequest.getString("remark");

        toolService.validateCreator(user.getId(), groupId);
        groupService.addMobile(groupId, mobile, remark);
        return new Response(0);
    }

    @Transactional
    @RequestMapping(value = "/{groupId}/mobile/addmultiple", method = RequestMethod.POST)
    public Response addMultipleMobile(@PathVariable("groupId") Long groupId, @RequestBody AddMultipleRequest request,
                                      HttpServletRequest httpServletRequest)
            throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");

        toolService.validateCreator(user.getId(), groupId);
        AddMultipleResponse response = new AddMultipleResponse();
        List<AddMultipleResponse.ErrorMessage> errorMessages = new ArrayList<>();
        Long i = new Long(0);
        for (MobileRemark mobileRemark : request.getMobiles()) {
            try {
                groupService.addMobile(groupId, mobileRemark.getMobile(), mobileRemark.getRemark());
            } catch (BaseException e) {
                AddMultipleResponse.ErrorMessage errorMessage = response.new ErrorMessage();
                errorMessage.setMobile(mobileRemark.getMobile());
                errorMessage.setRemark(mobileRemark.getRemark());
                errorMessage.setExceptionCode(e.getCode());
                errorMessage.setExceptionMessage(e.getMessage());
                errorMessage.setRow(i);
                errorMessages.add(errorMessage);
            }

            i += 1;
        }
        response.setErrorMessages(errorMessages);
        return new Response(0, response);
    }

    @Transactional
    @RequestMapping(value = "/{groupId}/mobile/delete", method = RequestMethod.DELETE)
    public Response deleteMobile(@PathVariable("groupId") Long groupId, @RequestBody JSONObject jsonRequest,
                                 HttpServletRequest httpServletRequest) throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String mobile = jsonRequest.getString("mobile");

        if(mobile == null){
            throw new NullKeyException("mobile");
        }

        toolService.validateCreator(user.getId(), groupId);
        groupService.deleteMobile(groupId, mobile);
        return new Response(0);
    }


    @RequestMapping(value = "/{groupId}/info",method = RequestMethod.GET)
    public Response getGroupInfo(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest)
            throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        toolService.validateCreator(user.getId(), groupId);

        GroupInfoResponse response = new GroupInfoResponse();
        response.setGroupId(groupId);
        response.setGroupName(groupService.getGroupNameById(groupId));
        response.setMobileRemarks(groupService.getGroupInfo(groupId));
        return new Response(0, response);
    }

    @RequestMapping(value = "/{groupId}/fuzzysearch",method = RequestMethod.POST)
    public Response fuzzySearch(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest,
                                @RequestBody JSONObject jsonRequest,
                                @RequestParam(required = false,defaultValue = "1") int page,
                                @RequestParam(required = false,defaultValue = "10") int rows)
            throws
            BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String pattern = jsonRequest.getString("pattern");

        if (pattern == null){
            throw new NullKeyException("pattern");
        }
        toolService.validateCreator(user.getId(), groupId);
        List<MobileRemark> mobileRemarks = groupService.fuzzySearch(groupId, pattern,page,rows);
        return new Response(0, mobileRemarks);
    }


    @RequestMapping(value = "/{groupId}/excel",method = RequestMethod.POST)
    public Response addMobilesWithExcel(@RequestParam("groupExcel") MultipartFile groupExcel,@PathVariable("groupId") Long
            groupId,
                           HttpServletRequest httpServletRequest) throws BaseException{
        User user = (User) httpServletRequest.getAttribute("user");
        toolService.validateCreator(user.getId(), groupId);

        if(groupExcel == null){
            throw new NullKeyException("groupExcel");
        }

        System.out.print(groupExcel.getSize());
        if (groupExcel.isEmpty()) {
            throw new NullFileException();
        }
        if(groupExcel.getSize()>UPLOAD_FILE_MAXSIZE){
            throw new FileSizeLimitException();
        }

        String fileName = groupExcel.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        boolean isExist =false;
        for (String suffix:UPLOAD_FILE_TYPE){
            if(suffix.equals(suffixName)){
                isExist = true;
                break;
            }
        }
        if(!isExist){
            throw new ErrorFileTypeException();
        }

        String filePath ="/home/dgy/IdeaProjects/springBoot/NEUQer-Mail/src/main/resources/upload/";

        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            groupExcel.transferTo(dest);
            List<ExcelInfo> excelInfoList = Utils.readExcel(filePath+fileName,suffixName);
            File f = new File(filePath+fileName);
            if(f.exists()){
                f.delete();
            }

            AddMultipleResponse response = new AddMultipleResponse();
            List<AddMultipleResponse.ErrorMessage> errorMessages = new ArrayList<>();
            for (ExcelInfo excelInfo : excelInfoList) {
                try {
                    groupService.addMobile(groupId, excelInfo.getMobile(), excelInfo.getRemark());
                } catch (BaseException e) {
                    AddMultipleResponse.ErrorMessage errorMessage = response.new ErrorMessage();
                    errorMessage.setMobile(excelInfo.getMobile());
                    errorMessage.setRemark(excelInfo.getRemark());
                    errorMessage.setExceptionCode(e.getCode());
                    errorMessage.setExceptionMessage(e.getMessage());
                    errorMessage.setRow(new Long(excelInfo.getIndex()));
                    errorMessages.add(errorMessage);
                }
            }
            response.setErrorMessages(errorMessages);
            return new Response(0,response);
        } catch (Exception e) {
            throw new UnknownException(e.getMessage());
        }
    }
}
