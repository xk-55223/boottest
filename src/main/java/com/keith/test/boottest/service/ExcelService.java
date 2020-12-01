package com.keith.test.boottest.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

public interface ExcelService {

    void getIds() throws IOException;

    void toPdf();

    Workbook copyRow() throws IOException;

}
