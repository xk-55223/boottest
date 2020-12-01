package com.keith.test.boottest.utils;

/**
 * rabbit工具类目
 *
 * @author keith
 * @since 2020-11-28
 */

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class RabbitUtil {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    static {
        connectionFactory.setHost("119.29.154.71");
        //5672是RabbitMQ的默认端口号
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("keith");
        connectionFactory.setPassword("keith");
        connectionFactory.setVirtualHost("/keith");
    }

    public static Connection getConnection() {
        Connection conn;
        try {
            conn = connectionFactory.newConnection();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
