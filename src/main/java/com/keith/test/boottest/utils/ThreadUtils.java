package com.keith.test.boottest.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author keith
 * @since 2020-11-22
 */
public class ThreadUtils {
    /**
     * 可缓存线程池
     */
    private static final ExecutorService CACHE_THREAD_POOL;
    /**
     * 定长线程
     */
    private static final ExecutorService FIXED_THREAD_POOL;
    /**
     * 单例线程
     */
    private static final ExecutorService SINGLE_THREAD_POOL;
    /**
     * 定时线程
     */
    private static final ScheduledExecutorService SCHEDULED_THREAD_POOL;

    static {
        /**
         * 队列最大长度
         */
        int queueMaxSize = 1024;
        /**
         * 存活时间
         */
        long keepAlivedTime = 0L;
        /**
         * 拒绝策略
         */
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        /**
         * 当前CPU的数量
         */
        int cpuNum = Runtime.getRuntime().availableProcessors();
        /**
         * 线程工厂AddMessageReq
         * 可以自己实现自定线程名称
         * 异常处理抛出等
         */
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        FIXED_THREAD_POOL = new ThreadPoolExecutor(cpuNum + 1, cpuNum + 1, keepAlivedTime,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueMaxSize), threadFactory, abortPolicy);

        CACHE_THREAD_POOL = new ThreadPoolExecutor(0, cpuNum + 1, keepAlivedTime,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueMaxSize), threadFactory, abortPolicy);

        SINGLE_THREAD_POOL = new ThreadPoolExecutor(1, 1, keepAlivedTime,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueMaxSize), threadFactory, abortPolicy);

        SCHEDULED_THREAD_POOL = new ScheduledThreadPoolExecutor(cpuNum + 1, threadFactory, abortPolicy);
    }

    /**
     * 可变线程池
     * 核心线程数为当前CPU核数+1
     * 最大线程数为当前CPU核数*2
     * Executors 使用的SynchronousQueue队列
     */
    public static ExecutorService getCacheThreadPool() {
        return CACHE_THREAD_POOL;
    }

    /**
     * 定长线程池
     * 核心线程数为当前CPU核数+1
     */
    public static ExecutorService getFixedThreadPool() {
        return FIXED_THREAD_POOL;
    }

    /**
     * 单个线程 线程池
     *
     * @return
     */
    public static ExecutorService getSingleThreadPool() {
        return SINGLE_THREAD_POOL;
    }

    /**
     * 定时线程池
     *
     * @return
     */
    public static ScheduledExecutorService getScheduledThreadPool() {
        return SCHEDULED_THREAD_POOL;
    }
}
