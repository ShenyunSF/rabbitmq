package utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by zhenzhong on 2020/12/4
 */
public class RabbitmqUtil
{
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    static
    {
        connectionFactory.setHost("192.168.31.12");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("my_vhost");
    }


    public static Connection getConnection()
    {
        Connection connection;
        try
        {
            connection = connectionFactory.newConnection();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
