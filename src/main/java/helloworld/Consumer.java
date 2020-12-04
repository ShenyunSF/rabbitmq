package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.RabbitConstant;
import utils.RabbitmqUtil;
import utils.Reciver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by zhenzhong on 2020/12/4
 * 消息队列消费者
 */
public class Consumer
{
    public static void main(String[] args) throws IOException
    {
        final Connection connection = RabbitmqUtil.getConnection();

        //创建虚拟连接和mq交互
        final Channel channel = connection.createChannel();

        //绑定队列
        //参数1：队列名称
        // 是否持久化
        //是否队列私有化，false代表所有消费者都可以访问。 true代表只有第一次拥有它的消费者可以一致使用
        //是否自动深处，fa代表连接听到后不自动删除这个队列
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD , false, false, false, null);

        String message = "helloworld";
        //从mq中消费数据
        // 队列名称
        //是否自动确认收到消息，false表示手动编程来确认消息是否被消费成功，时MQ推荐的
        //要传入 DefaultConsumer 的实现类
        channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD, false, new Reciver(channel));
    }
}
