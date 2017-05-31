package com.neuqer.mail.mapper;

import com.neuqer.mail.domain.MobileRemark;
import com.neuqer.mail.common.MyMapper;
import com.neuqer.mail.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Hotown on 17/5/14.
 */
@Mapper
public interface GroupMapper extends MyMapper<Group> {
    Group getGroupById(@Param("id") Long id);
    List<MobileRemark> fuzzySearch(@Param("str") String str, @Param("groupId")Long groupId);
}
