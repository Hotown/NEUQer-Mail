package com.neuqer.mail.client.excel.impl;

import com.neuqer.mail.utils.Utils;
import org.apache.poi.xssf.usermodel.*;

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

    public XSSFWorkbook exportExcelDemo(String[] headers) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        // 设置默认列宽度
        sheet.setDefaultColumnWidth((short) 15);
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i ++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        return workbook;
    }
}
