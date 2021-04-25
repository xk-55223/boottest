package com.keith.test.boottest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础测试控制器
 *
 * @author keith
 * @since 2021-02-05
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class CommonTestController {

    @RequestMapping("/http-timeout")
    public String httpTimeOutTest() throws InterruptedException {
        log.info("测试一下先");
        Thread.sleep(5000);
        return "true";
    }
}
