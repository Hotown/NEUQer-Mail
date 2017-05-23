package com.neuqer.mail.model;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by Hotown on 17/5/22.
 */
@Table(name = "mobile_group")
public class MobileGroup implements BaseModel {

    @Column(name = "mobile_id")
    private long mobileId;

    @Column(name = "group_id")
    private long groupId;

    /**
     * 备注
     */
    private String remark;

    public long getMobileId() {
        return mobileId;
    }

    public void setMobileId(long mobileId) {
        this.mobileId = mobileId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
