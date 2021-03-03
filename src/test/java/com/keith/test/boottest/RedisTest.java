package com.keith.test.boottest;

import com.keith.test.boottest.utils.RedisLockUtil;
import com.keith.test.boottest.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis测试类
 *
 * @author keith
 * @since 2020-11-20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {
    @Autowired
    RedisLockUtil redisLockUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ValueOperations<String, String> valueOperations;

    @Test
    public void lockTest() {
        for (int i = 0; i < 9; i++) {
            ThreadUtils.getCacheThreadPool().submit(() -> {
                String lockKey = "lockKey";
                String uuid = UUID.randomUUID().toString();
                try {
                    if (redisLockUtil.tryLock(lockKey, uuid, 30)) {
                        long threadId = Thread.currentThread().getId();
                        log.info("线程{}开始工作", threadId);
                        TimeUnit.SECONDS.sleep(2);
                        log.info("线程{}工作结束", threadId);
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
    }

    @Test
    public void lockTest11() {
        String lockKey = "lockKey";
        while (true) {
            String uuid = UUID.randomUUID().toString();
            try {
                // 获取锁,获取成功后处理业务逻辑
                if (getLock(lockKey, uuid)) {
                    long threadId = Thread.currentThread().getId();
                    log.info("线程{}开始工作", threadId);
                    TimeUnit.SECONDS.sleep(2);
                    log.info("线程{}工作结束", threadId);
                    return;
                } else {
                    // 获取失败则休眠一秒后再次尝试获取锁
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (uuid.equals(redisTemplate.opsForValue().get(lockKey))) {
                    // 解锁
                    redisTemplate.delete(lockKey);
                }
            }
        }

    }

    /**
     * 获取锁
     *
     * @param lockKey 获取锁
     */
    private boolean getLock(String lockKey, String value) {
        /*Boolean lockResult = redisTemplate.opsForValue().setIfAbsent(lockKey, value);
        if (lockResult) {
            // 获取锁成功设置过期时间并返回成功
            redisTemplate.expire(lockKey, 30, TimeUnit.SECONDS);
            return true;
        }
        return false;*/
        return redisTemplate.opsForValue().setIfAbsent(lockKey, value, 30, TimeUnit.SECONDS);
    }

    @Test
    public void redisTest() {
        long startTime = System.currentTimeMillis();
        valueOperations.set("test100", "测试10000");
        long timeMillis = System.currentTimeMillis();
        String test100 = valueOperations.get("test100");
        log.info("cost:{}, result: {}", System.currentTimeMillis() - timeMillis, test100);
    }

    @Test
    public void lockTest1() {
        if (redisLockUtil.tryLock("keith11222", "tetetet0", 30)) {
            log.info("获取锁成功");
        } else {
            log.info("获取锁失败");
        }
    }
}
