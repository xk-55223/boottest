package com.keith.test.boottest.controller;

import com.keith.test.boottest.utils.RedisLockUtil;
import com.keith.test.boottest.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis测试
 *
 * @author keith
 * @since 2020-11-22
 */
@RestController
@RequestMapping("redis")
@Slf4j
public class RedisController {

    @Autowired
    RedisLockUtil redisLockUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ValueOperations<String, String> valueOperations;

    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("lock")
    public String redisLockTest() {
        for (int i = 0; i < 9; i++) {
            ThreadUtils.getFixedThreadPool().submit(() -> {
                String lockKey = "lockKey";
                String uuid = UUID.randomUUID().toString();
                long timeMillis = System.currentTimeMillis();
                try {
                    if (redisLockUtil.tryLock(lockKey, uuid, 30)) {
                        long threadId = Thread.currentThread().getId();
                        log.info("线程{}开始工作.时间：{}", threadId, (System.currentTimeMillis() - timeMillis) / 1000);
                        // 模拟处理业务逻辑,线程睡眠2s
                        TimeUnit.SECONDS.sleep(2);
                        log.info("线程{}工作结束.时间：{}", threadId, (System.currentTimeMillis() - timeMillis) / 1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (uuid.equalsIgnoreCase(valueOperations.get(lockKey))) {
                        redisLockUtil.unLock(lockKey);
                    }
                }
            });
        }

        return "success";
    }

    @RequestMapping("lock1")
    public String redisLockTest1() {
        for (int i = 0; i < 9; i++) {
            ThreadUtils.getFixedThreadPool().submit(() -> {
                String lockKey = "lockKey";
                RLock lock = redissonClient.getLock(lockKey);
                long timeMillis = System.currentTimeMillis();
                try {
                    lock.lock();
                    long threadId = Thread.currentThread().getId();
                    log.info("线程{}开始工作.时间：{}", threadId, (System.currentTimeMillis() - timeMillis) / 1000);
                    // 模拟处理业务逻辑,线程睡眠2s
                    TimeUnit.SECONDS.sleep(2);
                    log.info("线程{}工作结束.时间：{}", threadId, (System.currentTimeMillis() - timeMillis) / 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }

        return "success";
    }
}
