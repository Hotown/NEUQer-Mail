package com.neuqer.mail.mapper;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.model.MobileGroup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dgy on 17-5-24.
 */
public class MobileGroupMapperTest extends BaseTest {
    @Autowired
    MobileGroupMapper mobileGroupMapper;

    @Test
    public void selectTest(){
        MobileGroup mobileGroup = new MobileGroup();
        mobileGroup.setGroupId(new Long(3));

        List<MobileGroup> mobileGroups = mobileGroupMapper.select(mobileGroup);
        for (MobileGroup mobileGroup1:mobileGroups){
            System.out.print(mobileGroup1);
        }
    }
}