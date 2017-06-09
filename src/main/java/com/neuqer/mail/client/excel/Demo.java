package com.neuqer.mail.client.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * Created by Hotown on 17/6/9.
 */
public class Demo {
    private XSSFSheet sheet;    //表格类实例
    public LinkedList[] result;    //保存每个单元格的数据 ，使用的是一种链表数组的结构

    //读取excel文件，创建表格实例
    public void loadExcel(String filePath) {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            XSSFWorkbook workBook = new XSSFWorkbook(inStream);

            sheet = workBook.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(inStream!=null){
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //获取单元格的值
    private String getCellValue(XSSFCell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            //判断单元格数据的类型，不同类型调用不同的方法
            switch (cell.getCellType()) {
                //数值类型
                case Cell.CELL_TYPE_NUMERIC:
                    //进一步判断 ，单元格格式是日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        //数值
//                        double value = cell.getNumericCellValue();
//                        int intValue = (int) value;
//                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                        double mobile = cell.getNumericCellValue();
                        cellValue = new DecimalFormat("#").format(mobile);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                //判断单元格是公式格式，需要做一种特殊处理来得到相应的值
                case Cell.CELL_TYPE_FORMULA:{
                    try{
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }catch(IllegalStateException e){
                        cellValue = String.valueOf(cell.getRichStringCellValue());
                    }

                }
                break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }



    //初始化表格中的每一行，并得到每一个单元格的值
    public  void init(){
        int rowNum = sheet.getLastRowNum() + 1;
        result = new LinkedList[rowNum];
        for(int i=0;i<rowNum;i++){
            XSSFRow row = sheet.getRow(i);
            //每有新的一行，创建一个新的LinkedList对象
            result[i] = new LinkedList();
            for(int j=0;j<row.getLastCellNum();j++){
                XSSFCell cell = row.getCell(j);
                //获取单元格的值
                String str = getCellValue(cell);
                //将得到的值放入链表中
                result[i].add(str);
            }
        }
    }
    //控制台打印保存的表格数据
    public void show(){
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[i].size();j++){
                System.out.print(result[i].get(j) + "\t");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Demo poi = new Demo();
        poi.loadExcel("/Users/Hotown/WorkSpace/java_project/neuqer-mail-system/src/test/demo.xlsx");
        poi.init();
        poi.show();
    }

}
