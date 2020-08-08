package com.keith.test.boottest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.keith.test.boottest.entity.LogisticsService;
import com.keith.test.boottest.mapper.LogisticsServiceMapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoottestApplicationTests {
    @Autowired
    LogisticsServiceMapper logisticsServiceMapper;

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
     * 导出数据
     * @param params
     * @param exportUrl
     * @param exportCollection
     * @param exportClass
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
     * @param params
     * @param exportUrl
     * @param exportCollection
     * @param exportClass
     */
    private void exportXls(ExportParams params, String exportUrl, List<?> exportCollection, Class<?> exportClass) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(exportUrl);

        Workbook workbook = ExcelExportUtil.exportExcel(params, exportClass, exportCollection);
        workbook.write(outputStream);
        outputStream.close();
    }

}
