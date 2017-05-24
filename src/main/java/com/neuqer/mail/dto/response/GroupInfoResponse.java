package com.neuqer.mail.dto.response;

import com.neuqer.mail.domain.MobileRemark;

import java.util.List;

/**
 * Created by dgy on 17-5-23.
 */
public class GroupInfoResponse {
    private Long groupId;

    String groupName;

    List<MobileRemark> mobileRemarks;


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<MobileRemark> getMobileRemarks() {
        return mobileRemarks;
    }

    public void setMobileRemarks(List<MobileRemark> mobileRemarks) {
        this.mobileRemarks = mobileRemarks;
    }
}
