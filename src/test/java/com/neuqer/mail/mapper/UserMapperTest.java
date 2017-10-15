package com.neuqer.mail.mapper;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.utils.Utils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Hotown on 17/5/15.
 */
public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void saveUserTest() {
        User user = new User();

        user.setMobile("15032321389");
        user.setPassword("neuqer2016");
        user.setNickname("Hotown");
        user.setEmail("hotown0515@163.com");
        user.setCreatedAt(Utils.createTimeStamp());
        user.setUpdatedAt(Utils.createTimeStamp());

        userMapper.insert(user);
    }

    @Test
    public void updateUserMobile() {
        String mobile = "15032321389";
        long userId = 3;
    }

    @Test
    public void selectTest(){
        Long userId = new Long(5);
        User user = userMapper.selectByPrimaryKey(userId);
        System.out.print(user);
    }
}
