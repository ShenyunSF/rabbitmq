package workqueues;

import com.rabbitmq.client.*;
import utils.RabbitConstant;
import utils.RabbitmqUtil;

import java.io.IOException;

/**
 * created by zhenzhong on 2020/12/4
 */
public class SMSSender2
{
    public static void main(String[] args) throws IOException
    {
        final Connection connection = RabbitmqUtil.getConnection();
        final Channel    channel    = connection.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS,false,false,false,null);
        channel.basicQos(1);
        //如果不屑basicQos(1) 则自动MQ会将所有的请求平均发送给每一个消费者
        channel.basicConsume(RabbitConstant.QUEUE_SMS,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws
                    IOException
            {
                final String sms = new String(body);
                System.out.println("发送短信成功： "+ sms);

                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
               channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
