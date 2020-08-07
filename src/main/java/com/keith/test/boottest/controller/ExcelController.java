package com.keith.test.boottest.controller;

import com.keith.test.boottest.entity.LogisticsService;
import com.keith.test.boottest.service.ExcelService;
import com.keith.test.boottest.service.LogisticsServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    LogisticsServiceService logisticsServiceService;

    @Autowired
    ExcelService excelService;

    @RequestMapping("/write/logistics")
    public void writeLogisticsService() {
        logisticsServiceService.writeLogisticsServiceToExcel();
    }

    @RequestMapping("/logistics/list-all")
    public List<LogisticsService> listAllLogisticsService() {
        return logisticsServiceService.listAllLogisticsService();
    }

    @RequestMapping("/getId")
    public void getIds() throws IOException {
        excelService.getIds();
    }
}
