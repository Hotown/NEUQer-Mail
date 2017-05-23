package com.neuqer.mail.dto.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Hotown on 17/5/18.
 */
public class LoginRequest {
    @NotBlank
    private String mobile;
    @NotBlank
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
