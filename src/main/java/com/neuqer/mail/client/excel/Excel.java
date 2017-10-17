package com.neuqer.mail.client.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Created by Hotown on 17/6/9.
 */
public interface Excel {
    boolean deleteTempFile(String fileName);
}
