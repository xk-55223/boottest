package com.keith.test.boottest.service.impl;

import com.keith.test.boottest.entity.Help;
import com.keith.test.boottest.entity.LogisticsService;
import com.keith.test.boottest.mapper.HelpMapper;
import com.keith.test.boottest.mapper.LogisticsServiceMapper;
import com.keith.test.boottest.service.LogisticsServiceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class LogisticsServiceServiceImpl implements LogisticsServiceService {
    @Autowired
    LogisticsServiceMapper logisticsServiceMapper;

    @Autowired
    HelpMapper helpMapper;


    @Override
    public List<LogisticsService> listAllLogisticsService() {
        return logisticsServiceMapper.listAllLogisticsService();
    }

    @Override
    public void writeLogisticsServiceToExcel() {
        List<LogisticsService> logisticsServices = listAllLogisticsService();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("excelTemplate/赔付规则.xlsx");
        try {
            Workbook sheets = new XSSFWorkbook(resourceAsStream);
            resourceAsStream.close();
            sheets.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Sheet sheet = sheets.getSheetAt(0);

            for (int i = 0; i < logisticsServices.size(); i++) {
                LogisticsService logisticsService = logisticsServices.get(i);
                Row row = sheet.createRow(i + 2);
                Cell serviceCodeCell = row.getCell(0);
                Cell serviceNameCell = row.getCell(1);
                Cell lostDescCell = row.getCell(2);
                Cell enLostDescCell = row.getCell(3);
                Cell lostStandardCell = row.getCell(4);
                Cell enLostStandardCell = row.getCell(5);
                Cell damageDescCell = row.getCell(6);
                Cell enDamageDescCell = row.getCell(7);
                Cell damageStandardCell = row.getCell(8);
                Cell enDamageStandardCell = row.getCell(9);
                Cell timeDescCell = row.getCell(10);
                Cell enTimeDescCell = row.getCell(11);
                Cell timeStandardCell = row.getCell(12);
                Cell enTimeStandardCell = row.getCell(13);

                serviceCodeCell.setCellValue(logisticsService.getServiceCode());
                serviceNameCell.setCellValue(logisticsService.getServiceName());
                lostDescCell.setCellValue("1.物流商确定包裹丢失；\n" +
                        "2.包裹有境外接收信息但后续无轨迹更新（一周左右）的可进行开查，开查后境外确定包裹丢失；");
                enLostDescCell.setCellValue(i + ". english lost description");
                lostStandardCell.setCellValue("1.按申报金额赔偿货值，最高不超过包裹运费金额。\n" +
                        "2.另外退还包裹运费。");
                enLostStandardCell.setCellValue(i + ". english lost standard");

                damageDescCell.setCellValue("1.易碎品不提供赔偿\n" +
                        "2.运费不退\n" +
                        "3.需向境外提供cn24证明破损由物流商运输不当造成，通常较难获得物流商赔偿");
                enDamageDescCell.setCellValue(i + ". english damage description");
                damageStandardCell.setCellValue("1.物流商视内件破损程度进行判断赔偿金额。\n" +
                        "2.不退还包裹运费。");
                enDamageStandardCell.setCellValue(i + ". english damage standard");

                timeDescCell.setCellValue("（物流商预估，仅供参考）");
                enTimeDescCell.setCellValue(i + ". english time description");
                timeStandardCell.setCellValue("发起查询后2周到2个月，需要国外邮政确认");
                enTimeStandardCell.setCellValue(i + ".english time standard");
            }
            FileOutputStream outputStream = new FileOutputStream("E:/WORKSPACE/compensationDescription.xlsx");
            sheets.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeHelpToExcel() {
        List<Help> helps = helpMapper.listAllHelp();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("excelTemplate/帮助中心APP.xlsx");
        try {
            XSSFWorkbook sheets = new XSSFWorkbook(resourceAsStream);
            resourceAsStream.close();
            sheets.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            XSSFSheet sheet = sheets.getSheetAt(0);

            for (int i = 0; i < helps.size(); i++) {
                Help help = helps.get(i);
                XSSFRow row = sheet.createRow(i + 1);
                String helpContent = help.getHelpContent();
                if (helpContent.length() > 32700) {
                    /*for (int j = 0; j < helpContent.length() / 30000; j++) {
                        XSSFCell cellj = row.getCell(j + 2);
                        cellj.setCellValue(helpContent.substring(30000 * j, 30000 * (j + 1)));
                    }*/
                    FileOutputStream outputStream = new FileOutputStream("D:\\WorkSpace\\帮助id" + help.getHelpId() + ".html");
                    outputStream.write(help.getHelpContent().getBytes());
                    outputStream.flush();
                    outputStream.close();
                    log.info("帮助id：{}", help.getHelpId());
                } else {
                    XSSFCell cell = row.getCell(0);
                    XSSFCell cell1 = row.getCell(1);
                    XSSFCell cell2 = row.getCell(2);
                    cell.setCellValue(help.getHelpId());
                    cell1.setCellValue(help.getHelpTitle());
                    cell2.setCellValue(helpContent);
                }
            }
            FileOutputStream outputStream = new FileOutputStream("D:\\WorkSpace\\帮助中心APP.xlsx");
            sheets.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
