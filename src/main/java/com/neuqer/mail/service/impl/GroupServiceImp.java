package com.neuqer.mail.service.impl;

import com.github.pagehelper.PageHelper;
import com.neuqer.mail.client.ClientFactory;
import com.neuqer.mail.client.sms.SMSClient;
import com.neuqer.mail.domain.MobileRemark;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.Client.ApiException;
import com.neuqer.mail.exception.Group.GroupNotExistException;
import com.neuqer.mail.exception.Group.NullKeyException;
import com.neuqer.mail.exception.Mobile.IllegalMobileException;
import com.neuqer.mail.exception.Mobile.MobileExistException;
import com.neuqer.mail.exception.Mobile.MobileNotExistException;
import com.neuqer.mail.exception.UnknownException;
import com.neuqer.mail.mapper.GroupMapper;
import com.neuqer.mail.mapper.MobileGroupMapper;
import com.neuqer.mail.mapper.MobileMapper;
import com.neuqer.mail.model.Group;
import com.neuqer.mail.model.Mobile;
import com.neuqer.mail.model.MobileGroup;
import com.neuqer.mail.service.GroupService;
import com.neuqer.mail.utils.Utils;
import com.neuqer.mail.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dgy on 17-5-17.
 */
@Service("GroupService")
public class GroupServiceImp extends BaseServiceImpl<Group, Long> implements GroupService {

    @Autowired
    MobileMapper mobileMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    MobileGroupMapper mobileGroupMapper;


    @Override
    public void createGroup(String groupName, Long userId) throws BaseException {

        long curTime = Utils.createTimeStamp();
        Group group = new Group();
        group.setGroupName(groupName);
        group.setCreatedAt(curTime);
        group.setUpdatedAt(curTime);
        group.setUserId(userId);
        if (groupMapper.insert(group) != 1) {
            throw new UnknownException("Failed to createGroup group!");
        }
        return;
    }

    @Override
    public void deleteGroup(Long groupId) throws BaseException {

        MobileGroup mobileGroup = new MobileGroup();
        mobileGroup.setGroupId(groupId);
        mobileGroupMapper.delete(mobileGroup);

        if (groupMapper.deleteByPrimaryKey(groupId) != 1) {
            throw new UnknownException("Fail to delete group!");
        }
    }

    @Override
    public void updateGroupName(Group group, String newName) throws BaseException {
        group.setGroupName(newName);
        group.setUpdatedAt(Utils.createTimeStamp());
        if (groupMapper.updateByPrimaryKeySelective(group) != 1) {
            throw new UnknownException("Fail to update group name!");
        }
    }

    @Override
    public void addMobile(Long groupId, String mobile, String remark) throws BaseException {

        if (mobile == null) {
            throw new NullKeyException("mobile");
        }

        if (remark == null) {
            remark = mobile;
        }

        if (!Validator.validateMobile(mobile)) {
            throw new IllegalMobileException();
        }

        if (groupMapper.getGroupById(groupId) == null) {
            throw new GroupNotExistException();
        }

        Mobile mobileObj = new Mobile();
        mobileObj.setMobile(mobile);
        mobileObj = mobileMapper.selectOne(mobileObj);
        if (mobileObj == null) {
            mobileObj = new Mobile();
            Long curTime = Utils.createTimeStamp();
            mobileObj.setUpdatedAt(curTime);
            mobileObj.setCreatedAt(curTime);
            mobileObj.setMobile(mobile);

            if (mobileMapper.insert(mobileObj) != 1) {
                throw new UnknownException("插入mobileObj时失败");
            }
        }

        MobileGroup mobileGroup = new MobileGroup();
        mobileGroup.setGroupId(groupId);
        mobileGroup.setMobileId(mobileObj.getId());
        mobileGroup.setRemark(remark);

        try {
            if (mobileGroupMapper.insert(mobileGroup) != 1) {
                throw new UnknownException("Fail to insert mobileGroup");
            }
        } catch (DuplicateKeyException e) {
            throw new MobileExistException(mobile);
        }

    }

    @Override
    public void deleteMobile(Long groupId, String mobile) throws BaseException {
        if (!Validator.validateMobile(mobile)) {
            throw new IllegalMobileException();
        }

        Mobile mobileObj = new Mobile();
        mobileObj.setMobile(mobile);
        mobileObj = mobileMapper.selectOne(mobileObj);
        if (mobileObj == null) {
            throw new MobileNotExistException();
        }

        MobileGroup mobileGroup = new MobileGroup();
        mobileGroup.setGroupId(groupId);
        mobileGroup.setMobileId(mobileObj.getId());
        if (mobileGroupMapper.delete(mobileGroup) != 1) {
            throw new UnknownException("Fail to delete mobile");
        }
    }

    @Override
    public String getGroupNameById(Long groupId) throws BaseException {
        Group group = groupMapper.selectByPrimaryKey(groupId);
        if (group == null) {
            throw new GroupNotExistException();
        }
        return group.getGroupName();
    }

    @Override
    public List<Group> getGroupsInfo(Long userId) throws BaseException {
        Group group = new Group();
        group.setUserId(userId);
        return select(group);
    }

    @Override
    public List<MobileRemark> getGroupInfo(Long groupId) throws BaseException {
        Group group = groupMapper.selectByPrimaryKey(groupId);

        if (group == null) {
            throw new GroupNotExistException();
        }
        List<MobileRemark> mobileRemarks = new ArrayList<>();

        MobileGroup mobileGroup = new MobileGroup();
        mobileGroup.setGroupId(groupId);
        List<MobileGroup> mobileGroups = mobileGroupMapper.select(mobileGroup);

        for (MobileGroup mobileGroupObj : mobileGroups) {

            Mobile modelMobile = mobileMapper.selectByPrimaryKey(mobileGroupObj.getMobileId());
            if (modelMobile == null) {
                throw new UnknownException("id为" + mobileGroupObj.getMobileId() + "的手机号不存在！");
            }

            MobileRemark mobileRemark = new MobileRemark();
            mobileRemark.setRemark(mobileGroupObj.getRemark());
            mobileRemark.setMobile(modelMobile.getMobile());
            mobileRemarks.add(mobileRemark);
        }
        return mobileRemarks;
    }

    @Override
    public List<MobileRemark> fuzzySearch(Long groupId, String str, int pageNum, int pageSize) throws BaseException {
        PageHelper.startPage(pageNum, pageSize);
        str = "%" + str + "%";
        return groupMapper.fuzzySearch(str, groupId);
    }

    @Override
    public boolean sendWithGroup(Long groupId, String message) throws BaseException, IOException {
        MobileGroup mobileGroup = new MobileGroup();
        mobileGroup.setGroupId(groupId);
        List<MobileGroup> mobileGroupList = mobileGroupMapper.select(mobileGroup);
        LinkedList<String> mobiles = new LinkedList<String>();
        for (MobileGroup m : mobileGroupList) {
            mobiles.add(mobileMapper.selectByPrimaryKey(m.getMobileId()).getMobile());
        }

        SMSClient client = (SMSClient) ClientFactory.getClient("SMS");
        for (String mobile : mobiles) {
            String apiResult = client.accountPswdMobileMsgGet(mobile, message);
            int begin = apiResult.indexOf(",");
            int end = apiResult.indexOf(" ");
            if (!"0".equals(apiResult.substring(begin + 1 , end))) {
                throw new ApiException();
            }
        }
        return true;
    }
}
