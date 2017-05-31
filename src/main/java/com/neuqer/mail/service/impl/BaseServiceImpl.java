package com.neuqer.mail.service.impl;

import com.neuqer.mail.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hotown on 17/5/15.
 */
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

    /**
     * 泛型注入
     */
    @Autowired
    private Mapper<T> mapper;

    @Override
    public T selectByPrimaryKey(PK entityId) {
        return mapper.selectByPrimaryKey(entityId);
    }

    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }

    @Override
    public int deleteByPrimaryKey(PK entityId) {
        return mapper.deleteByPrimaryKey(entityId);
    }

    @Override
    public int save(T record) {
        return mapper.insert(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }
}
