package com.neuqer.mail.utils;

import com.neuqer.mail.common.ExcelCommon;

import java.io.File;
import java.util.UUID;

/**
 * Created by Hotown on 17/5/15.
 */
public class Utils {
    public static Long createTimeStamp() {
        return System.currentTimeMillis();
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取路径中的后缀
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || ExcelCommon.EMPTY.equals(path)) {
            return ExcelCommon.EMPTY;
        }
        if (path.contains(ExcelCommon.POINT)) {
            return path.substring(path.lastIndexOf(ExcelCommon.POINT) + 1, path.length());
        }
        return ExcelCommon.EMPTY;
    }

    public static boolean deleteTempExcel(String filePath) {
        File tempFile = new File(filePath);
        if (!tempFile.exists()) {
            return false;
        } else {
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            if (suffix.equals(".xls") || suffix.equals(".xlsx")) {
                return tempFile.delete();
            }
        }
        return false;
    }
}
