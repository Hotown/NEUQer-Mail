package com.neuqer.mail.mapper;

import com.neuqer.mail.common.MyMapper;
import com.neuqer.mail.model.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Hotown on 17/5/15.
 */
@Mapper
public interface TokenMapper extends MyMapper<Token> {
    Token getTokenByUserId(@Param("userId") long userId);

    Token getTokenByTokenStr(@Param("token") String token);
}
