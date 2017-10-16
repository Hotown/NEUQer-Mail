package com.neuqer.mail.client.excel.impl;

import com.neuqer.mail.utils.Utils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Hotown on 17/6/9.
 */
public class XlsxExcel extends AbstractExcel {
    //表格类实例
    private XSSFSheet sheet;
    //保存每个单元格的数据
    public LinkedList[] result;

    public XlsxExcel(String filePath) {
        loadExcel(filePath);
        init();
    }

    private boolean loadExcel(String filePath) {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            XSSFWorkbook workBook = new XSSFWorkbook(inStream);
            sheet = workBook.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean init() {
        int rowNum = sheet.getLastRowNum() + 1;
        result = new LinkedList[rowNum];
        for (int i = 0; i < rowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            //每有新的一行，创建一个新的LinkedList对象
            result[i] = new LinkedList();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                XSSFCell cell = row.getCell(j);
                //获取单元格的值
                String str = getCellValue(cell);
                //将得到的值放入链表中
                result[i].add(str);
            }
        }
        return true;
    }
}
