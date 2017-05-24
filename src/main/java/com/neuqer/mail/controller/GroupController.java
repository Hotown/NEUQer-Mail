package com.neuqer.mail.controller;


import com.alibaba.fastjson.JSONObject;
import com.neuqer.mail.common.Response;
import com.neuqer.mail.dto.request.AddMultipleRequest;
import com.neuqer.mail.domain.MobileRemark;
import com.neuqer.mail.dto.response.AddMultipleResponse;
import com.neuqer.mail.dto.response.GroupInfoResponse;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Group;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.GroupService;
import com.neuqer.mail.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 创建群组
     *
     * @param httpServletRequest
     * @param jsonRequest
     * @return
     * @throws BaseException
     */

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response createGroup(HttpServletRequest httpServletRequest, @RequestBody JSONObject jsonRequest) throws
            BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String groupName = jsonRequest.getString("groupName");

        groupService.createGroup(groupName, user.getId());
        return new Response(0);
    }


    /**
     * 删除群组
     *
     * @param groupId            要删除的群组id
     * @param httpServletRequest
     * @return
     * @throws BaseException
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
     * 修改群名
     *
     * @param groupId            要修改的群的id
     * @param httpServletRequest
     * @param jsonObject
     * @return
     * @throws BaseException
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
     * 向群组中添加单个手机号
     *
     * @param groupId
     * @param jsonRequest
     * @param httpServletRequest
     * @return
     * @throws BaseException
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
     * 批量向群组中添加手机号
     *
     * @param groupId
     * @param request
     * @param httpServletRequest
     * @return
     * @throws BaseException
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
     * 删除手机号
     *
     * @param groupId
     * @param jsonRequest
     * @param httpServletRequest
     * @return
     * @throws BaseException
     */
    @Transactional
    @RequestMapping(value = "/{groupId}/mobile/delete", method = RequestMethod.DELETE)
    public Response deleteMobile(@PathVariable("groupId") Long groupId, @RequestBody JSONObject jsonRequest,
                                 HttpServletRequest httpServletRequest) throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String mobile = jsonRequest.getString("mobile");

        toolService.validateCreator(user.getId(), groupId);
        groupService.deleteMobile(groupId, mobile);
        return new Response(0);
    }

    /**
     * 获得群组信息
     *
     * @param groupId
     * @param httpServletRequest
     * @return
     * @throws BaseException
     */
    @RequestMapping("/{groupId}/info")
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
     * 模糊组内查询
     *
     * @param groupId
     * @param httpServletRequest
     * @param jsonRequest
     * @return
     * @throws BaseException
     */
    @RequestMapping("/{groupId}/fuzzysearch")
    public Response fuzzySearch(@PathVariable("groupId") Long groupId, HttpServletRequest httpServletRequest,
                                @RequestBody JSONObject jsonRequest) throws BaseException {
        User user = (User) httpServletRequest.getAttribute("user");
        String pattern = jsonRequest.getString("pattern");
        toolService.validateCreator(user.getId(), groupId);
        List<MobileRemark> mobileRemarks = groupService.fuzzySearch(groupId, pattern);
        return new Response(0, mobileRemarks);
    }
}
