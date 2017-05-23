package com.neuqer.mail.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hotown on 17/5/15.
 */
public interface BaseService<T, PK extends Serializable> {

    T selectByPrimaryKey(PK entityId);

    T selectOne(T record);

    int deleteByPrimaryKey(PK entityId);

    int save(T record);

    /**
     * 根据主键，更新实体所有字段，null值也会被更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据主键更新不为null的值
     *
     * @param record
     * @return
     */

}
