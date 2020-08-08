package com.keith.test.boottest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.keith.test.boottest.entity.LogisticsService;
import com.keith.test.boottest.mapper.LogisticsServiceMapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Excel测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcelTest {
    @Autowired
    LogisticsServiceMapper logisticsServiceMapper;

    /**
     * 导出excel测试(.xlsx)
     * @throws IOException IO异常
     */
    @Test
    public void exportXlsx() throws IOException {
        List<LogisticsService> logisticsServices = logisticsServiceMapper.listAllLogisticsService();

        ExportParams params = new ExportParams("标题", "物流数据");

        String exportUrl = "E:\\WORKSPACE\\物流路径.xlsx";

        exportXlsx(params, exportUrl, logisticsServices, LogisticsService.class);

        String exportXls = "E:\\WORKSPACE\\物流路径X.xls";

        exportXls(params, exportXls, logisticsServices, LogisticsService.class);
    }

    /**
     * 导出excel测试(.xls)
     * @param params            标题和表名数据
     * @param exportUrl         导出磁盘地址
     * @param exportCollection  导出集合
     * @param exportClass       导出类
     */
    private void exportXlsx(ExportParams params, String exportUrl, List<?> exportCollection, Class<?> exportClass) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(exportUrl);

        Workbook workbook = ExcelExportUtil.exportBigExcel(params, exportClass, exportCollection);
        ExcelExportUtil.closeExportBigExcel();
        workbook.write(outputStream);
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
    public void inputExcel() {
        String url = "E:\\WORKSPACE\\物流路径X.xls";

        List<LogisticsService> logisticsServices = importExcel(url, 1, LogisticsService.class);

        logisticsServices.forEach(System.out :: println);
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

}
