package com.neuqer.mail.service.impl;

import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.Group.GroupNotExistException;
import com.neuqer.mail.exception.Group.NotCreatorException;
import com.neuqer.mail.mapper.GroupMapper;
import com.neuqer.mail.model.Group;
import com.neuqer.mail.service.GroupService;
import com.neuqer.mail.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dgy on 17-5-18.
 */
@Service("ToolService")
public class ToolServiceImp implements ToolService{
    @Autowired
    GroupMapper groupMapper;

    @Override
    public Group validateCreator(Long userId, Long groupId)throws BaseException {
        Group group = groupMapper.getGroupById(groupId);

        if (group == null){
            throw new GroupNotExistException();
        }

        if(!group.getUserId().equals(userId)){
            throw new NotCreatorException();
        }
        return group;
    }
}
