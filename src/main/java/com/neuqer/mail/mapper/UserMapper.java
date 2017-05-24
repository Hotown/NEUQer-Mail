package com.neuqer.mail.mapper;

import com.neuqer.mail.common.MyMapper;
import com.neuqer.mail.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Hotown on 17/5/14.
 */
@Mapper
public interface UserMapper extends MyMapper<User> {
    User getUserByMobile(@Param("mobile") String mobile);

    int updateNickname(@Param("userId") long userId, @Param("nickname") String nickname);

    int updateUserAvatar(@Param("userId") long userId, @Param("url") String url);

    int updatePassword(@Param("userId") long userId, @Param("password") String password);
}
