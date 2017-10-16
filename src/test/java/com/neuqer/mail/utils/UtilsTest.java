package com.neuqer.mail.utils;

import com.neuqer.mail.BaseTest;
import org.junit.Test;

public class UtilsTest extends BaseTest {
    @Test
    public void deleteTempExcel() {
        String fileName = "/Users/Hotown/WorkSpace/java_project/NEUQer-Mail/src/main/resources/upload/aa.xls";
        System.out.println(Utils.deleteTempExcel(fileName));
    }
}
