package com.neuqer.mail.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dgy on 17-5-18.
 */
public class Validator {
    /**
     * 验证是手机合法行性
     * @param mobile
     * @return
     */
    public static boolean validateMobile(String mobile){
        String pattern = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(mobile);
        return matcher.matches();
    }
}
