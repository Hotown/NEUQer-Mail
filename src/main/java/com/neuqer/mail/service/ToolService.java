package com.neuqer.mail.service;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.model.Group;

/**
 * Created by dgy on 17-5-18.
 */
public interface ToolService {
    public Group validateCreator(Long userId, Long groupId)throws BaseException;
}
