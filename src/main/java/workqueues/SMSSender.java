package workqueues;

import com.rabbitmq.client.*;
import utils.RabbitConstant;
import utils.RabbitmqUtil;

import java.io.IOException;

/**
 * created by zhenzhong on 2020/12/4
 */
public class SMSSender
{
    public static void main(String[] args) throws IOException
    {
        final Connection connection = RabbitmqUtil.getConnection();
        final Channel    channel    = connection.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS,false,false,false,null);

        //
        //如果不写basicQos(1) 则自动MQ会将所有的请求平均发送给每一个消费者
        //basicQos 表示MQ不会再对消费者一次发送多个请求，而是消费者处理完一个数据后（确认后），再从队列中再回去一个新的
        channel.basicQos(1);//处理完一个再取一个
        channel.basicConsume(RabbitConstant.QUEUE_SMS,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws
                    IOException
            {
                final String sms = new String(body);
                System.out.println("发送短信成功： "+ sms);

                try{
                    Thread.sleep(10);
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
