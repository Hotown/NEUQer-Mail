package com.neuqer.mail.interceptor;

import com.neuqer.mail.exception.Auth.NeedLoginException;
import com.neuqer.mail.exception.UnknownException;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Hotown on 17/5/23.
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;


    private ArrayList<String> uncheckUrlNormal = new ArrayList<String>() {{
        add("/user/register");
        add("/user/login");
    }};

    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = httpServletRequest.getHeader("token");
        String nowUrl = httpServletRequest.getRequestURI();

        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            return true;
        }

        for (String url : uncheckUrlNormal) {
            if (nowUrl.equals(url)) {
                return true;
            }
        }

        if (token == null || token.equals("")) {
            throw new NeedLoginException();
        }

        if (tokenService == null) {
            throw new UnknownException();
        }

        User user = tokenService.verifyToken(token);
        httpServletRequest.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
