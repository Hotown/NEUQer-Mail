package com.neuqer.mail.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neuqer.mail.common.Response;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;

/**
 * Created by Hotown on 17/5/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private User user;
    private String token;

    public LoginResponse(User user, Token token) {
        this.user = user;
        this.token = token.getToken();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
