package com.keith.test.boottest;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * pdf测试类
 *
 * @author keith
 * @since 2020-12-17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PdfTest {

    @Test
    public void exportXlsx() throws IOException {
        //加载PDF测试文档


        PdfDocument pdf = new PdfDocument();


        pdf.loadFromFile("C:\\Users\\26914\\Desktop\\文档\\物流相关\\dhl发票\\test.pdf");


        //保存为Excel文档

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\26914\\Desktop\\文档\\物流相关\\dhl发票\\ToExcel.xlsx");
        pdf.saveToStream(byteArrayOutputStream, FileFormat.XLSX);
        pdf.dispose();
        XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        workbook.write(fileOutputStream);


        /*
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.shiftRows(1, 1, -1);//删除第一行到第四行，然后使下方单元格上移
        workbook.write(new FileOutputStream("C:\\Users\\26914\\Desktop\\文档\\物流相关\\dhl发票\\ToExcel.xlsx"));
        */
    }
}
