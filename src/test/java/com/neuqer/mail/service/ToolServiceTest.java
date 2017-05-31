package com.neuqer.mail.service;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.exception.BaseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dgy on 17-5-24.
 */
public class ToolServiceTest extends BaseTest {
    @Autowired
    ToolService toolService;
    @Test
    public void validateCreatorTest()throws BaseException{
        Long userId = new Long(5);

    }
}
