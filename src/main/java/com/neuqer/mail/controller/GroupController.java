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

    /**
     * @apiDefine CODE_200
     * @apiSuccess (Code) {number} code 状态码0,请求成功
     */

    /**
     * @api{post} /group/create [创建群组]
     * @apiName CreateGroup
     * @apiGroup Group
     * @apiUse TOKEN
     *
     * @apiParam {String} groupName 群组名称 (Not Null)
     *
     * @apiParamExample {json} Request-Example:
     *{
     *      "groupName":"newGroup6"
     *}
     *
     * @apiUse CODE_200
     *
     * @apiSuccessExample {json} Success-Response:
     *{
     *      "code": 0
     *}
     */
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

    /**
     * @api{delete} /group/:groupId/delete [删除群组]
     * @apiName DeleteGroup
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 删除群组
     *
     * @apiParam {Number} groupId 被删除的群组Id
     *
     * @apiUse CODE_200
     *
     * @apiSuccessExample {json} Success-Response:
     *{
     *      "code": 0
     *}
     */
    @Transactional
    @RequestMapping(value = "/{groupId}/delete", method = RequestMethod.DELETE)
    public Response deleteGroup(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest)
            throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        toolService.validateCreator(user.getId(), groupId);
        groupService.deleteGroup(groupId);

        return new Response(0);
    }

    /**
     * @api{put} /group/:groupId/name [修改群名]
     * @apiName UpdateGroupName
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 修改群名
     *
     * @apiParam {Number} groupId 要修改的群组Id
     * @apiParam {String} newName 修改后的名字
     * @apiSuccessExample {json} Success-Response:
     * {
     * "newName":"newName2"
     * }
     *
     * @apiUse CODE_200
     *
     * @apiSuccessExample {json} Success-Response:
     *{
     *      "code": 0
     *}
     */
    @RequestMapping(value = "/{groupId}/name", method = RequestMethod.PUT)
    public Response updateGroupName(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest,
                                    @RequestBody JSONObject jsonObject) throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String newName = jsonObject.getString("newName");

        Group group = toolService.validateCreator(user.getId(), groupId);
        groupService.updateGroupName(group, newName);

        return new Response(0);
    }

    /**
     * @api{post} /group/:groupId/mobile/addsingle [添加单个手机号]
     * @apiName addSingleMobile
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 向群组中添加单个手机号
     *
     * @apiParam {Number} groupId 目标群组Id
     * @apiParam {String} mobile 手机号
     * @apiParam {String} remark 备注
     *
     * @apiSuccessExample {json} Success-Response:
     * {
     * "mobile":"13847859461",
     * "remark":"丁光耀"
     * }
     * @apiUse CODE_200
     *
     * @apiSuccessExample {json} Success-Response:
     *{
     *      "code": 0
     *}
     */

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

    /**
     * @api{post} /group/:groupId/mobile/addmultiple [批量向群组中添加手机号]
     * @apiName addMultipleMobile
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 批量向群组中添加手机号
     *
     * @apiParam {Number} groupId 目标群组Id
     * @apiParam {Array} mobiles 批量倒入的手机信息列表，是AddMultipleRequest类的对象,包含mobile，remark俩个属性
     *      @apiParam {String} mobile 手机号
     *      @apiParam {String} remark 备注
     *
     * {
    "mobiles":[
    {
    "mobile":"15603321818",
    "remark":"1"
    },
    {
    "mobile":"15603321819",
    "remark":"2"
    },
    {
    "mobile":"15603321820",
    "remark":"3"
    }
    ]
    }
     *
     * @apiUse CODE_200
     * @apiSuccess (Data) {Array} errorMessages 保存未成功导入群组的手机的信息
     * @apiSucess （ErrorMessage）{string} mobile 未成功导入项的手机号
     * @apiSucess （ErrorMessage）{string} remark 未成功导入项的备注
     * @apiSucess （ErrorMessage）{Number} row 未成功导入项的索引值
     * @apiSucess （ErrorMessage）{Number} exceptionCode 未成功导入项的错误码
     * @apiSucess （ErrorMessage）{String} exceptionMessage 未成功导入项的错误信息
     * @apiSuccessExample {json} Success-Response:
     *
     * {
    "code": 0,
    "data": {
    "errorMessages": [
    {
    "mobile": "15603321818",
    "remark": "1",
    "row": 0,
    "exceptionCode": 30002,
    "exceptionMessage": "15603321818 exist in this group!"
    },
    {
    "mobile": "15603321819",
    "remark": "2",
    "row": 1,
    "exceptionCode": 30002,
    "exceptionMessage": "15603321819 exist in this group!"
    },
    {
    "mobile": "15603321820",
    "remark": "3",
    "row": 2,
    "exceptionCode": 30002,
    "exceptionMessage": "15603321820 exist in this group!"
    }
    ]
    }
    }
     */
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

    /**
     * @api{delete} /group/:groupId/mobile/delete [删除手机号]
     * @apiName deleteMobile
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 从群组中删除指定的手机号
     *
     * @apiParam {Number} groupId 目标群组Id
     * @apiParam {String} mobile 要删除的手机号（Not null）
     * @apiParamExample {json} Request-Example:
     * {
     * "mobile":"15603321111"
     * }
     *
     */
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

    /**
     * @api{get} /group/:groupId/info [获得群组信息]
     * @apiName getGroupInfo
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 获得群组信息
     *
     *
     * @apiUse CODE_200
     * @apiSuccess (Data) {Number} groupId 群组id
     * @apiSuccess (Data) {String} groupName 群组名称
     * @apiSuccess (Data) {String} groupName 群组名称
     * @apiSuccess (Data) {Array} mobileRemarks 群内手机、备注信息列表
     * @apiSucess （MobileRemark）{string} mobile 手机号
     * @apiSucess （MobileRemark）{string} remark 备注
     *@apiSuccessExample {json} Success-Response:
     *
     * {
    "code": 0,
    "data": {
    "groupId": 4,
    "groupName": "newName2",
    "mobileRemarks": [
    {
    "mobile": "15603321818",
    "remark": "1"
    },
    {
    "mobile": "15603321819",
    "remark": "test2"
    },
    {
    "mobile": "15603321820",
    "remark": "test3"
    }
    ]
    }
    }
     */
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
    /**
     * @api{post} /group/:groupId/fuzzysearch [模糊搜索]
     * @apiName fuzzySearch
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 根据所给字段从手机号和备注中进行模糊搜索
     *
     * @apiParam {String} pattern 模式串（Not Null）
     * @apiParamExample {json} Request-Example:
     * {
     *      "pattern":"156"
     * }
     *
     * @apiUse CODE_200
     * @apiSuccess  {Array} data 搜索到的手机、备注列表
     * @apiSucess （Data）{string} mobile 手机号
     * @apiSucess （Data）{string} remark 备注
     * @apiSuccessExample {json} Success-Response:
     *
     * {
    "code": 0,
    "data": [
    {
    "mobile": "15603321818",
    "remark": "1"
    },
    {
    "mobile": "15603321819",
    "remark": "test2"
    },
    {
    "mobile": "15603321820",
    "remark": "test3"
    },
    {
    "mobile": "15603329999",
    "remark": "1"
    }
    ]
    }
     */
    @RequestMapping(value = "/{groupId}/fuzzysearch",method = RequestMethod.POST)
    public Response fuzzySearch(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest,
                                @RequestBody JSONObject jsonRequest) throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String pattern = jsonRequest.getString("pattern");
        if (pattern == null){
            throw new NullKeyException("pattern");
        }
        toolService.validateCreator(user.getId(), groupId);
        List<MobileRemark> mobileRemarks = groupService.fuzzySearch(groupId, pattern);
        return new Response(0, mobileRemarks);
    }
    /**
     * @api{post} /group/:groupId/excel [通过excel导入手机号]
     * @apiName AddMobilesWithExcel
     * @apiGroup Group
     * @apiUse TOKEN
     * @apiDescription 根据所给字段从手机号和备注中进行模糊搜索
     *
     * @apiParam {MultipartFile} groupExcel 需要上传的excel（Not Null）
     *
     * @apiUse CODE_200
     * @apiSuccess (Data) {Array} errorMessages 保存未成功导入群组的手机的信息
     * @apiSucess （ErrorMessage）{string} mobile 未成功导入项的手机号
     * @apiSucess （ErrorMessage）{string} remark 未成功导入项的备注
     * @apiSucess （ErrorMessage）{Number} row 未成功导入项所在的行
     * @apiSucess （ErrorMessage）{Number} exceptionCode 未成功导入项的错误码
     * @apiSucess （ErrorMessage）{String} exceptionMessage 未成功导入项的错误信息
     * @apiSuccessExample {json} Success-Response:
     * {
    "code": 0,
    "data": {
    "errorMessages": [
    {
    "mobile": "15603321818",
    "remark": "1",
    "row": 0,
    "exceptionCode": 30002,
    "exceptionMessage": "15603321818 exist in this group!"
    },
    {
    "mobile": "15603321819",
    "remark": "2",
    "row": 1,
    "exceptionCode": 30002,
    "exceptionMessage": "15603321819 exist in this group!"
    },
    {
    "mobile": "15603321820",
    "remark": "3",
    "row": 2,
    "exceptionCode": 30002,
    "exceptionMessage": "15603321820 exist in this group!"
    }
    ]
    }
    }
     *
     *
     */

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
