package com.neuqer.mail.interceptor;

import com.neuqer.mail.exception.Auth.NeedLoginException;
import com.neuqer.mail.exception.Auth.TokenExpiredException;
import com.neuqer.mail.model.Token;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.TokenService;
import com.neuqer.mail.service.UserService;
import com.neuqer.mail.utils.Utils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    private static final ArrayList<String> uncheckUrlNormal = new ArrayList<String>(){{
        add("/user/register");
        add("/user/login");
    }};

    public TokenInterceptor(UserService userService, TokenService tokenService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = httpServletRequest.getHeader("token");
        String url = httpServletRequest.getRequestURI();

        // OPTIONS请求不作处理
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 过滤uncheckUrl
        for (String uncheck : uncheckUrlNormal) {
            if (uncheck.equals(url)) {
                return true;
            }
        }

        if (token == null || token.equals("")) {
            throw new NeedLoginException();
        }

        Token tk = tokenService.getTokenByTokenStr(token);

        if (tk == null || tk.equals("")) {
            throw new NeedLoginException();
        }

        if (tk.getExpiredAt() < Utils.createTimeStamp()) {
            throw new TokenExpiredException();
        }

        User user = userService.selectByPrimaryKey(tk.getUserId());

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
