package utils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * created by zhenzhong on 2020/12/4
 */
public class Reciver extends DefaultConsumer
{
    private Channel channel;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public Reciver(Channel channel)
    {
        super(channel);
        this.channel=channel;
    }

    //签收数据

    /**
     * @param consumerTag
     * @param envelope    每个消息的id
     * @param properties
     * @param body        消费的消息
     * @return void
     * @author zhenzhong
     * @date 2020/12/4 23:11
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws
            IOException
    {
        final String reciveMessage = new String(body);
        System.out.println("receiver message:  " + reciveMessage);
        System.out.println("消费者接收到的消息的标签： " + envelope.getDeliveryTag());

        //表示签收消息，并且把签收的ack返回
        //false 表示只签收当前的消息 true表示把以前未签收的消息也签收
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
