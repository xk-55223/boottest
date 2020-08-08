package com.keith.test.boottest.service.impl;

import com.keith.test.boottest.service.ExcelService;
import com.keith.test.boottest.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExcelServiceImpl implements ExcelService {
    public static final Pattern pattern = Pattern.compile("id=([0-9]+)");
    public static final Pattern pattern1 = Pattern.compile("([0-9]+)[.]html");

    @Override
    public void getIds() throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("excelTemplate/第三方链接排重后.xlsx");
        Workbook workbook = new XSSFWorkbook(resourceAsStream);
        resourceAsStream.close();
        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Sheet sheet = workbook.getSheetAt(0);

        List<String> stringList = new ArrayList<>(16384);
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(2);
            String url = ExcelUtil.getCellFormatValue(cell);
            if (StringUtils.isEmpty(url)) {
                continue;
            }
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                String id = matcher.group(1);
                if (url.contains("taobao")) {
                    stringList.add("TB" + id);
                }

                if (url.contains("tmall")) {
                    stringList.add("TMALL" + id);
                }
                continue;
            }

            Matcher matcher1 = pattern1.matcher(url);
            if (matcher1.find()) {
                String id = matcher1.group(1);
                stringList.add("ALIBABA" + id);
            }
        }

        InputStream emptyStream = this.getClass().getClassLoader().getResourceAsStream("excelTemplate/empty.xlsx");
        Workbook hssfWorkbook = new XSSFWorkbook(emptyStream);
        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Sheet sheetAt = hssfWorkbook.getSheetAt(0);

        for (int i = 0; i < stringList.size(); i++) {
            Row row = sheetAt.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(stringList.get(i));
        }

        FileOutputStream outputStream = new FileOutputStream("E:/WORKSPACE/商品编号.xlsx");
        hssfWorkbook.write(outputStream);
        outputStream.close();
    }

    public static void main(String[] args) {
        Matcher matcher = pattern.matcher("https://item.taobao.com/item.htm?id=571325534804");
        if (matcher.find()) {
            String group = matcher.group(1);
            System.out.println(group);
        }

        Matcher matcher1 = pattern1.matcher("https://detail.1688.com/offer/541390608702.html");
        if (matcher1.find()) {
            String group = matcher1.group(1);
            System.out.println(group);
        }
    }
}
