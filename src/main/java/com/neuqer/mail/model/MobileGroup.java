package com.neuqer.mail.model;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by Hotown on 17/5/22.
 */
@Table(name = "mobile_group")
public class MobileGroup implements BaseModel {

    @Column(name = "mobile_id")
    private Long mobileId;

    @Column(name = "group_id")
    private Long groupId;

    /**
     * 备注
     */
    private String remark;

    public Long getMobileId() {
        return mobileId;
    }

    public void setMobileId(Long mobileId) {
        this.mobileId = mobileId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MobileGroup{" +
                "mobileId=" + mobileId +
                ", groupId=" + groupId +
                ", remark='" + remark + '\'' +
                '}';
    }
}
