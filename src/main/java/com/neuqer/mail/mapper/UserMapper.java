package com.neuqer.mail.mapper;

import com.neuqer.mail.common.MyMapper;
import com.neuqer.mail.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyMapper<User> {

}
