package com.neuqer.mail.mapper;

import com.github.pagehelper.PageHelper;
import com.neuqer.mail.BaseTest;
import com.neuqer.mail.domain.MobileRemark;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dgy on 17-5-24.
 */
public class GroupMapperTest extends BaseTest{
    @Autowired
    GroupMapper groupMapper;

    @Test
    public void fuzzySearchTest(){
        Long groupId = new Long(6);
        String str = "%156%";

        PageHelper.startPage(3,3);

        List<MobileRemark> mobileRemarks = groupMapper.fuzzySearch(str,groupId);
        for (MobileRemark mobileRemark:mobileRemarks){
            System.out.println(mobileRemark);
        }
    }
}
