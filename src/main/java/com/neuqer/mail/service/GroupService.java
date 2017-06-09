package com.neuqer.mail.service;

import com.neuqer.mail.domain.MobileRemark;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Group;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dgy on 17-5-17.
 */
@Service
public interface GroupService {

    void createGroup(String groupName, Long userId) throws BaseException;

    void deleteGroup(Long groupId) throws BaseException;

    void updateGroupName(Group group, String newName) throws BaseException;

    void addMobile(Long groupId, String mobile, String remark) throws BaseException;

    void deleteMobile(Long groupId, String mobile) throws BaseException;

    String getGroupNameById(Long groupId) throws BaseException;

    List<Group> getGroupsInfo(Long userId) throws BaseException;

    List<MobileRemark> getGroupInfo(Long groupId) throws BaseException;

    List<MobileRemark> fuzzySearch(Long groupId, String str, int pageNum, int pageSize) throws BaseException;
}
