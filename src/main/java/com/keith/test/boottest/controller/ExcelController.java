package com.keith.test.boottest.controller;

import com.keith.test.boottest.entity.LogisticsService;
import com.keith.test.boottest.service.ExcelService;
import com.keith.test.boottest.service.LogisticsServiceService;
import com.keith.test.boottest.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {
    @Autowired
    LogisticsServiceService logisticsServiceService;

    @Autowired
    ExcelService excelService;

    @RequestMapping("/write/logistics")
    public void writeLogisticsService() {
        logisticsServiceService.writeLogisticsServiceToExcel();
    }

    @RequestMapping("/write/help")
    public void writeHelp() {
        logisticsServiceService.writeHelpToExcel();
    }

    @RequestMapping("/logistics/list-all")
    public List<LogisticsService> listAllLogisticsService() {
        return logisticsServiceService.listAllLogisticsService();
    }

    @RequestMapping("/getId")
    public void getIds() throws IOException {
        excelService.getIds();
    }

    @RequestMapping("/http/test")
    public void httpTest() throws Exception {
        String result = HttpClientUtil.httpGet("http://parcelway-cn-test-temp.oss-accelerate.aliyuncs.com/parcel/invoice/2020/11/12/invoice-b5f34183b30e54023cb277376ae3ac96-P1605150997.pdf", null, null);
        String base64 = Base64Utils.encodeToString(result.getBytes());
        System.out.println(base64);
    }

    @RequestMapping("to-pdf")
    public void toPdf(HttpServletResponse httpServletResponse) {
        excelService.toPdf();
    }

    @RequestMapping("/copy-row")
    public void copyRow(HttpServletResponse response) throws IOException {
        Workbook workbook = excelService.copyRow();
        try {
            response.setContentType("application/octet-stream");
            // 是否内联附件
            String inlineType = "attachment";
            String fileName = "printInvoiceTest" + DateUtils.formatDate(new Date(), "yyyy-MM-dd") + ".xlsx";
            response.setHeader("Content-Disposition", inlineType + ";filename=\"" + fileName + "\"");
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            log.error("printInvoiceTest error.", e);
        }
    }
}
