package com.neuqer.mail.client.excel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by Hotown on 17/6/9.
 */
public interface Excel {
    public void loadExcel(String filePath);

    public String getCellValue(Cell cell);

    public void init();
}
