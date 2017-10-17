package com.neuqer.mail.client.excel.impl;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;


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

    public HSSFWorkbook exportExcelDemo(String[] headers) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        // 设置默认列宽度
        sheet.setDefaultColumnWidth((short) 15);
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i ++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        return workbook;
    }
}
