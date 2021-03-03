package com.keith.test.boottest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * 并发测试
 *
 * @author keith
 * @since 2021-02-19
 */
@SpringBootTest
@Slf4j
public class ThreadTest {
    private static Map<String, String> baseMap = new HashMap<>();

    private static boolean booltest = true;

    private volatile static int counter = 0;

    static {
        baseMap.put("keith", "testResult");
    }

    @Test
    public void threadTest() {
        new Thread(() -> {
            while (booltest) {
                log.info("boolTest true");
            }

            log.info("boolTest Result");
        }).start();

        new Thread(() -> {
            booltest = false;
            log.info("boolchange");
        }).start();
    }

    public static void main(String[] args) {
        /*new Thread(() -> {
            while (true) {
                System.out.println(baseMap.get("keith"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "printThread").start();

        new Thread(() -> {
            baseMap.put("keith", "hasChange");
            System.out.println("changeResult:success");
        }, "changeThread").start();*/


        /*new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (booltest) {
                log.info("boolTest true");
            }

            log.info("boolTest Result");
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            booltest = false;
            log.info("boolchange");
        }).start();*/

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter++;
                }
            }).start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
    }
}
