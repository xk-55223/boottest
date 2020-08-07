package com.keith.test.boottest.service;

import com.keith.test.boottest.entity.LogisticsService;

import java.util.List;

public interface LogisticsServiceService {

    List<LogisticsService> listAllLogisticsService();

    void writeLogisticsServiceToExcel();
}
