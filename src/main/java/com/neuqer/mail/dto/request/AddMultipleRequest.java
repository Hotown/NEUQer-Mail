package com.neuqer.mail.dto.request;

import com.neuqer.mail.domain.MobileRemark;

import java.util.List;

/**
 * Created by dgy on 17-5-22.
 */
public class AddMultipleRequest {

    private List<MobileRemark> mobiles;

    public AddMultipleRequest() {
    }

    public List<MobileRemark> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<MobileRemark> mobiles) {
        this.mobiles = mobiles;
    }
}
