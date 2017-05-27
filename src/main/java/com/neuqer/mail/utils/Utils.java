package com.neuqer.mail.utils;

import com.neuqer.mail.controller.GroupController;
import com.neuqer.mail.domain.ExcelInfo;
import com.neuqer.mail.domain.MobileRemark;
import com.neuqer.mail.exception.BaseException;
import com.neuqer.mail.exception.File.ErrorFileTypeException;
import com.neuqer.mail.exception.UnknownException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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

    public static List<ExcelInfo> readExcel(String fileName, String suffixName){
        switch (suffixName){
            case ".xlsx":
                return readExcelWithXssf(fileName);
            case ".xls":
                return readExcelWithHssf(fileName);
            default:
                throw new ErrorFileTypeException();
        }
    }
    public static List<ExcelInfo> readExcelWithXssf(String fileName)throws BaseException{
        try
        {
            InputStream ins =new FileInputStream(new File(fileName));
            @SuppressWarnings("resource")
            XSSFWorkbook wb = new XSSFWorkbook(ins);
            ins.close();

            XSSFSheet sheet = wb.getSheetAt(0);
            if(sheet == null){
                throw new UnknownException("sheet is null");
            }
            List<ExcelInfo> excelInfoList = new ArrayList<>();
            XSSFCell cell = null;

            for (int i = sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
                XSSFRow row = sheet.getRow(i);
                if(row == null){
                    continue;
                }
                ExcelInfo excelInfo = new ExcelInfo();
                cell = row.getCell(0);

                if (cell == null){
                    continue;
                }
                switch (cell.getCellType()){
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        double mobile = cell.getNumericCellValue();
                        excelInfo.setMobile(new DecimalFormat("#").format(mobile));
                        break;
                    case XSSFCell.CELL_TYPE_STRING:
                        excelInfo.setMobile(cell.getStringCellValue());
                        break;
                    default:
                        excelInfo.setMobile(cell.toString());
                }
                cell = row.getCell(1);
                if(cell == null){
                    excelInfo.setRemark(excelInfo.getMobile());
                }else {
                    switch (cell.getCellType()){
                        case XSSFCell.CELL_TYPE_STRING:
                            excelInfo.setRemark(cell.getStringCellValue());
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            double mobile = cell.getNumericCellValue();
                            excelInfo.setRemark(new DecimalFormat("#").format(mobile));
                            break;
                        default:
                            excelInfo.setRemark(cell.toString());
                    }
                }
                excelInfo.setIndex(i);
                excelInfoList.add(excelInfo);
            }
            return excelInfoList;
        }catch (Exception e){
            throw new UnknownException(e.getMessage());
        }
    }
    public static List<ExcelInfo> readExcelWithHssf(String fileName)throws BaseException{
        try
        {
            InputStream ins =new FileInputStream(new File(fileName));
            @SuppressWarnings("resource")
            HSSFWorkbook wb = new HSSFWorkbook(ins);
            ins.close();

            HSSFSheet sheet = wb.getSheetAt(0);
            if(sheet == null){
                throw new UnknownException("sheet is null");
            }
            List<ExcelInfo> excelInfoList = new ArrayList<>();
            HSSFCell cell = null;

            for (int i = sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
                HSSFRow row = sheet.getRow(i);
                if(row == null){
                    continue;
                }
                ExcelInfo excelInfo = new ExcelInfo();
                cell = row.getCell(0);

                if (cell == null){
                    continue;
                }
                switch (cell.getCellType()){
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        double mobile = cell.getNumericCellValue();
                        excelInfo.setMobile(new DecimalFormat("#").format(mobile));
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        excelInfo.setMobile(cell.getStringCellValue());
                        break;
                    default:
                        excelInfo.setMobile(cell.toString());
                }
                cell = row.getCell(1);
                if(cell == null){
                    excelInfo.setRemark(excelInfo.getMobile());
                }else {
                    switch (cell.getCellType()){
                        case HSSFCell.CELL_TYPE_STRING:
                            excelInfo.setRemark(cell.getStringCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            double mobile = cell.getNumericCellValue();
                            excelInfo.setRemark(new DecimalFormat("#").format(mobile));
                            break;
                        default:
                            excelInfo.setRemark(cell.toString());
                    }
                }
                excelInfo.setIndex(i);
                excelInfoList.add(excelInfo);
            }
            return excelInfoList;
        }catch (Exception e){
            throw new UnknownException(e.getMessage());
        }
    }
}
