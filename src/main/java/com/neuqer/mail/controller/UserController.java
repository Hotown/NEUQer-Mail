package com.neuqer.mail.controller;

import com.alibaba.fastjson.JSONObject;
import com.neuqer.mail.common.Response;
import com.neuqer.mail.dto.request.LoginRequest;
import com.neuqer.mail.dto.request.RegisterRequest;
import com.neuqer.mail.exception.BadRequestException;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.UnknownException;
import com.neuqer.mail.exception.User.PasswordErrorException;
import com.neuqer.mail.model.BaseModel;
import com.neuqer.mail.model.User;
import com.neuqer.mail.service.UserService;
import com.neuqer.mail.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * Created by Hotown on 17/5/18.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户注册
     *
     * @param request RegisterRequest dto 对象
     * @return token, user
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody @Valid RegisterRequest request) throws BaseException {
        String mobile = request.getMobile();

        String pwd = request.getPassword();

        return new Response<>(0,
                userService.registerUser(mobile, pwd));
    }

    /**
     * 用户登录
     *
     * @param request LoginRequest dto对象
     * @return LoginResponse dto对象
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Response login(@RequestBody @Valid LoginRequest request) throws BaseException {
        String mobile = request.getMobile();
        String pwd = request.getPassword();

        return new Response<>(0,
                userService.login(mobile, pwd));
    }

    /**
     * 登出
     *
     * @param request
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public Response logout(HttpServletRequest request) throws BaseException {
        User user = (User) request.getAttribute("user");

        if (!userService.logout(user.getId())) {
            throw new UnknownException();
        }

        return new Response(0);
    }

    /**
     * 查看个人信息
     *
     * @param request
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public Response getUserInfo(HttpServletRequest request) throws BaseException {
        User user = (User) request.getAttribute("user");

        if (user != null) {
            user.setPassword(null);
        } else {
            throw new UnknownException();
        }

        HashMap<String, BaseModel> data = new HashMap<String, BaseModel>() {{
            put("user", user);
        }};

        return new Response(0, data);
    }

    /**
     * 修改用户昵称
     *
     * @param request
     * @param httpServletRequest
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/nickname", method = RequestMethod.PUT)
    public Response updateNickname(@RequestBody JSONObject request, HttpServletRequest httpServletRequest) throws BaseException {
        String nickname = request.getString("nickname");
        if (nickname == null) {
            throw new BadRequestException();
        }

        User user = (User) httpServletRequest.getAttribute("user");
        if (!userService.updateNickname(user.getId(), nickname)) {
            throw new UnknownException("fail to update nickname.");
        }

        return new Response(0);
    }

    /**
     * 修改密码
     *
     * @param request
     * @param httpServletRequest
     * @return
     * @throws BaseException
     */
    @ResponseBody
    @RequestMapping(path = "/password", method = RequestMethod.PUT)
    public Response updateUserPassword(@RequestBody JSONObject request, HttpServletRequest httpServletRequest) throws BaseException {
        String originPwd = request.getString("password");
        String pwd = request.getString("newPassword");
        User user = (User) httpServletRequest.getAttribute("user");

        // 加密验证
        originPwd = EncryptionUtil.getHash(originPwd, "MD5");

        if (!originPwd.equals(user.getPassword())) {
            throw new PasswordErrorException();
        }

        if (!userService.updatePassword(user.getId(), pwd)) {
            throw new UnknownException("fail to update password");
        }

        return new Response(0);
    }
}
