package com.neuqer.mail.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 * Created by Hotown on 17/5/14.
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
