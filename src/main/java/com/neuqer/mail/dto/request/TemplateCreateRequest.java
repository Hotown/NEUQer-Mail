package com.neuqer.mail.dto.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Hotown on 17/5/25.
 */
public class TemplateCreateRequest {
    @NotBlank
    private String tempName;

    private String content;

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
