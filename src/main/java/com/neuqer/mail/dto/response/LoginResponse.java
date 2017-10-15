package com.neuqer.mail.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Hotown on 17/5/18.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private User user;
    private String token;

    public LoginResponse(User user, Token token) {
        user.setPassword("****");
        this.user = user;
        this.token = token.getToken();
    }
}
