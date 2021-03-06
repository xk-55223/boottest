package com.keith.test.boottest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.handler.inter.IWriter;
import cn.afterturn.easypoi.pdf.PdfExportUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.keith.test.boottest.dto.LogisticsErrorCode;
import com.keith.test.boottest.dto.TranslateTemplate;
import com.keith.test.boottest.dto.YouDaoLangTemplate;
import com.keith.test.boottest.entity.Help;
import com.keith.test.boottest.entity.LogisticsService;
import com.keith.test.boottest.entity.ProductExist;
import com.keith.test.boottest.mapper.HelpMapper;
import com.keith.test.boottest.mapper.LogisticsServiceMapper;
import com.keith.test.boottest.utils.HttpClientUtil;
import com.keith.test.boottest.utils.PdfUtil;
import com.keith.test.boottest.utils.TranslateUtils;
import com.keith.test.boottest.utils.UUIDGenerator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Excel测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelTest {
    @Autowired
    LogisticsServiceMapper logisticsServiceMapper;

    @Autowired
    HelpMapper helpMapper;

    /**
     * 导出excel测试(.xlsx)
     * @throws IOException IO异常
     */
    @Test
    public void exportXlsx() throws IOException {
        List<Help> helps = helpMapper.listAllHelp();
        ExportParams params = new ExportParams("标题", "物流数据");

        String exportUrl = "D:\\\\WorkSpace\\帮助中心.xlsx";
//        String exportXls = "D:\\WorkSpace\\帮助中心.xls";
        ArrayList<ExcelExportEntity> excelExportEntities = new ArrayList<>();
        for (Help help : helps) {
            excelExportEntities.add(help);
        }
        exportXlsx(params, exportUrl, excelExportEntities);

//        exportXls(params, exportXls, helps, Help.class);
    }

    /**
     * 导出excel测试(.xls)
     * @param params            标题和表名数据
     * @param exportUrl         导出磁盘地址
     * @param exportCollection  导出集合
     */
    private void exportXlsx(ExportParams params, String exportUrl, List<ExcelExportEntity> exportCollection) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(exportUrl);

        IWriter<Workbook> workbook = ExcelExportUtil.exportBigExcel(params, exportCollection);
        Workbook workbook1 = workbook.get();
        workbook1.write(outputStream);
        outputStream.close();
    }

    /**
     * 导出数据
     * @param params            标题和表名数据
     * @param exportUrl         导出磁盘地址
     * @param exportCollection  导出集合
     * @param exportClass       导出类
     */
    private <T> void exportXls(ExportParams params, String exportUrl, List<T> exportCollection, Class<T> exportClass) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(exportUrl);

        Workbook workbook = ExcelExportUtil.exportExcel(params, exportClass, exportCollection);
        workbook.write(outputStream);
        outputStream.close();
    }

    /**
     * 导入测试
     */
    @Test
    public void inputExcel() throws IOException {
        String url = "C:\\Users\\26914\\Desktop\\文档\\翻译\\有道\\语言枚举.xlsx";
        String txtUrl = "C:\\Users\\26914\\Desktop\\文档\\日语工作表.xlsx";

//        List<LogisticsService> logisticsServices = importExcel(url, 1, LogisticsService.class);
        //List<TranslateTemplate> translateTemplates = importExcel(url, 1, YouDaoLangTemplate.class);
        List<YouDaoLangTemplate> translateTemplates = importExcel(url, 1, YouDaoLangTemplate.class);
        StringBuilder stringBuilder = new StringBuilder();
        for (YouDaoLangTemplate translateTemplate : translateTemplates) {
            stringBuilder.append(translateTemplate.getEnName().toUpperCase().replaceAll(" ", "_"));
            stringBuilder.append("(\"").append(translateTemplate.getCode()).append("\", \"").append(translateTemplate.getCnName()).append("\"),\n");
        }

        System.out.println(stringBuilder.toString());

        //        Map<String, TranslateTemplate> map = translateTemplates.stream().collect(Collectors.toMap(TranslateTemplate::getSource, i -> i));
        //        List<String> linkList = productExistList.stream().map(i -> getUrl(i.getPlatform(), i.getGoodsCode())).filter(Objects::nonNull).collect(Collectors.toList());
        /*FileInputStream fileOutputStream = new FileInputStream(txtUrl);
        XSSFWorkbook workbook = new XSSFWorkbook(fileOutputStream);
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheetAt.getRow(i);
            XSSFCell cell = row.getCell(0);
            TranslateTemplate template = map.get(cell.getStringCellValue());
            String result = template == null ? cell.getStringCellValue() : template.getTarget();
            cell.setCellValue(result);
        }*/

        //workbook.write(new FileOutputStream(txtUrl));
    }

    /**
     * 根据商品id获取淘宝的链接
     *
     * @param id 商品id
     * @return 链接
     */
    private String getUrl(String platform, String id) {
        if ("TB".equalsIgnoreCase(platform)) {
            return "https://item.taobao.com/item.htm?id=" + id;
        }

        if ("TMALL".equalsIgnoreCase(platform)) {
            return "https://detail.tmall.com/item.htm?id=" + id;
        }

        return null;
    }

    /**
     * 导入工具
     * @param url           磁盘地址
     * @param headRow       属性名行号
     * @param importClass   接受类
     * @param <T>           泛型
     * @return              集合
     */
    private <T> List<T> importExcel(String url, int headRow, Class<T> importClass) {
        File file = new File(url);

        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(headRow);

        return ExcelImportUtil.importExcel(file, importClass, importParams);
    }

    @Test
    public void excel2pdf() throws Exception {
    }

    /*@Test
    public void genLogisticsErrorCode() {
        List<LogisticsErrorCode> translateTemplates = importExcel("C:\\Users\\26914\\Desktop\\错误码导入20210325(已自动还原).xlsx", 1, LogisticsErrorCode.class);
        for (LogisticsErrorCode translateTemplate : translateTemplates) {
            translateTemplate.setEnMessage(TranslateUtils.translateSingle(translateTemplate.getErrorMessage(), "zh-CHS", "en"));
        }

        String errorSqlTemplate = "INSERT INTO `starit_frame_platform_rewrite`.`sys_error_code_config`(`error_code`, `lang`, `hint_code`, `hint_info`, `placeholder_count`, `code_desc`, `status`, `create_time`, `update_time`, `del_flag`, `data_code`, `create_by`, `update_by`) VALUES ";
        String cnTemplate = "('#{uuid}', 'cn', '#{errorCode}', '#{errorMessage}', 0, NULL, 1, #{currTime}, #{currTime}, '0', '#{uuid}', 'keith', 'keith'),\r\n";
        String enTemplate = "('#{uuid}', 'en', '#{errorCode}', '#{errorMessage}', 0, NULL, 1, #{currTime}, #{currTime}, '0', '#{uuid}', 'keith', 'keith'),\r\n";
        StringBuilder result = new StringBuilder(errorSqlTemplate);
        for (LogisticsErrorCode translateTemplate : translateTemplates) {
            long timeMillis = System.currentTimeMillis();
            result.append(cnTemplate.replace("#{uuid}", UUIDGenerator.getUUID())
                    .replace("#{uuid}", UUIDGenerator.getUUID())
                    .replace("#{errorCode}", translateTemplate.getErrorCode())
                    .replace("#{errorMessage}", translateTemplate.getErrorMessage().replaceAll("\'", "\""))
                    .replace("#{currTime}", String.valueOf(timeMillis))
                    .replace("#{currTime}", String.valueOf(timeMillis)));
            result.append(enTemplate.replace("#{uuid}", UUIDGenerator.getUUID())
                    .replace("#{uuid}", UUIDGenerator.getUUID())
                    .replace("#{errorCode}", translateTemplate.getErrorCode())
                    .replace("#{errorMessage}", translateTemplate.getEnMessage().replaceAll("\'", "\""))
                    .replace("#{currTime}", String.valueOf(timeMillis))
                    .replace("#{currTime}", String.valueOf(timeMillis)));
        }
        result.deleteCharAt(result.length() - 1).append(";");
        System.out.println(result.toString());
    }*/

}
