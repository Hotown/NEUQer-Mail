package com.neuqer.mail.client.excel.impl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class XlsExcel extends AbstractExcel {
    private HSSFSheet sheet;
    public LinkedList[] result;

    public XlsExcel(String filePath) {
        loadExcel(filePath);
        init();
    }

    private boolean loadExcel(String filePath) {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            HSSFWorkbook workBook = new HSSFWorkbook(inStream);
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

    public boolean init() {
        int rowNum = sheet.getLastRowNum() + 1;
        result = new LinkedList[rowNum];
        for (int i = 0; i < rowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            //每有新的一行，创建一个新的LinkedList对象
            result[i] = new LinkedList();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                HSSFCell cell = row.getCell(j);
                //获取单元格的值
                String str = getCellValue(cell);
                //将得到的值放入链表中
                result[i].add(str);
            }
        }
        return true;
    }
}
