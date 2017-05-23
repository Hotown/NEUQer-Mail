package com.neuqer.mail.utils;

import java.util.UUID;

/**
 * Created by Hotown on 17/5/15.
 */
public class Utils {
    public static Long createTimeStamp() {
        return System.currentTimeMillis();
    }
    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
