package com.keith.test.boottest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis锁工具
 *
 * @author keith
 * @since 2020-11-20
 */
@Component
public class RedisLockUtil {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ValueOperations<String, String> valueOperations;

    public boolean tryLock(String key, String value, int expireTime) {
        while (true) {
            Boolean aBoolean = valueOperations.setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
            if (aBoolean) {
                return true;
            } else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean unLock(String key) {
        return redisTemplate.delete(key);
    }
}
