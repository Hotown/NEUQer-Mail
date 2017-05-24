package com.neuqer.mail.domain;

/**
 * Created by dgy on 17-5-24.
 */
public class MobileRemark {
    String mobile;

    String remark;

    public MobileRemark() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MobileRemark{" +
                "mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
