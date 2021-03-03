package com.keith.test.boottest;

import com.keith.test.boottest.utils.HttpClientUtil;
import com.keith.test.boottest.utils.ThreadUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 基础测试
 *
 * @author keith
 * @since 2021-02-05
 */
public class CommonTest {

    public static void main(String[] args) {
        ExecutorService cacheThreadPool = ThreadUtils.getCacheThreadPool();
        Future<String> future = cacheThreadPool.submit(() -> HttpClientUtil.httpGet("http://localhost:8044/test/http-timeout", null, null));
        String response = null;
        long timeMillis = System.currentTimeMillis();
        try {
            response = future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("超时了：cost：" + (System.currentTimeMillis() - timeMillis) + ", message:" + e.getMessage());
        }

        System.out.println("cost: " + (System.currentTimeMillis() - timeMillis) + ",result:" + response);
    }
}
