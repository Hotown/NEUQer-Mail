package com.neuqer.mail.client.excel;

import com.neuqer.mail.common.ExcelCommon;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.File.ErrorFileTypeException;
import com.neuqer.mail.utils.Utils;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Hotown on 17/6/9.
 */
public class ReadExcel {
    //保存每个单元格的数据
    private LinkedList[] result;

    /**
     * 读取Excel文件
     *
     * @param path
     * @return
     * @throws IOException
     * @throws BaseException
     */
    public LinkedList[] readExcel(String path) throws IOException, BaseException {
        if (path == null || ExcelCommon.EMPTY.equals("path")) {
            return null;
        } else {
            String postfix = Utils.getPostfix(path);
            if (!ExcelCommon.EMPTY.equals(postfix)) {
                if (ExcelCommon.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path);
                } else if (ExcelCommon.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                } else {
                    throw new ErrorFileTypeException();
                }
            }
        }
        return null;
    }

    /**
     * 处理后缀为.xls的文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public LinkedList[] readXls(String path) throws IOException {
        return null;
    }

    /**
     * 处理后缀为.xlsx的文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public LinkedList[] readXlsx(String path) throws IOException {
        return null;
    }
}
