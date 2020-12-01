package com.keith.test.boottest.utils;


import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
//import com.aspose.cells.WorkbookSettings;
//import com.spire.xls.FileFormat;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfUtil {

    public static void excelToPdf() throws IOException {
//        com.spire.xls.Workbook workbook = new com.spire.xls.Workbook();
//        workbook.loadFromStream(new FileInputStream("D:\\第三方软件\\浏览器下载\\invoice-551104f566388865c9adfd85899f906b-P1599467515.xlsx"));
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        workbook.saveToStream(byteArrayOutputStream, FileFormat.PDF);
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\第三方软件\\浏览器下载\\spire.pdf");

//        fileOutputStream.write(byteArrayOutputStream.toByteArray());
    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = PdfUtil.class.getClassLoader().getResourceAsStream("xlsxlicense.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void excel2pdf() {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\第三方软件\\浏览器下载\\invoice-551104f566388865c9adfd85899f906b-P1599467515.xlsx");
            Workbook wb = new Workbook(fileInputStream);
            FileOutputStream fileOS = new FileOutputStream("D:\\第三方软件\\浏览器下载\\aspose测试.pdf");
            wb.save(fileOS, SaveFormat.PDF);
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        excel2pdf();
        excelToPdf();
    }
}
