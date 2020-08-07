package com.keith.test.boottest;

import com.keith.test.boottest.entity.LogisticsService;
import com.keith.test.boottest.mapper.LogisticsServiceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoottestApplicationTests {
    @Autowired
    LogisticsServiceMapper logisticsServiceMapper;

    @Test
    public void contextLoads() {
        List<LogisticsService> logisticsServices = logisticsServiceMapper.listAllLogisticsService();
        for (LogisticsService logisticsService : logisticsServices) {
            System.out.println(logisticsService);
        }
    }

}
