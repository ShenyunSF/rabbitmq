package workqueues;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitConstant;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by zhenzhong on 2020/12/4
 * 工作队列模式： 多个消费者消费.适用于任务过多或者过重的场景： 12306 发送短信，实现异步提速
 */
public class OrderSystem
{
    public static void main(String[] args) throws IOException, TimeoutException
    {
        final Connection connection = RabbitmqUtil.getConnection();
        final Channel    channel    = connection.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);

        for (int i = 0; i < 100; i++)
        {
            final SMS    sms     = new SMS("乘客" + i, "mobil" + i, "您的车票已经预定成功");
            final String jsonSms = new Gson().toJson(sms);
            channel.basicPublish("", RabbitConstant.QUEUE_SMS, false, null, jsonSms.getBytes());
        }
        System.out.println("发送数据成功");
        channel.close();
        connection.close();

    }
}
