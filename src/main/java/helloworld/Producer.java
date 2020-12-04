package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import utils.RabbitConstant;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by zhenzhong on 2020/12/4
 */
public class Producer
{
    //docker pull rabbitmq:3.7.7-management

    //docker run -d --name rabbitmq3.7.7 -p 5672:5672 -p 15672:15672 -v `pwd`/data:/var/lib/rabbitmq --hostname myRabbit
    // -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin 2888deb59dfc

    /*   -d 后台运行容器；
           --name 指定容器名；
           -p 指定服务运行的端口（5672：应用访问端口；15672：控制台Web端口号）；
           -v 映射目录或文件；
           --hostname  主机名（RabbitMQ的一个重要注意事项是它根据所谓的 “节点名称” 存储数据，默认为主机名）；
           -e 指定环境变量；（RABBITMQ_DEFAULT_VHOST：默认虚拟机名；RABBITMQ_DEFAULT_USER：默认的用户名；RABBITMQ_DEFAULT_PASS：默认用户名的密码）
   */
    public static void main(String[] args) throws IOException, TimeoutException
    {
        final Connection connection = RabbitmqUtil.getConnection();

        //创建虚拟连接和mq交互
        final Channel channel = connection.createChannel();

        //队列名称
        // 是否持久化
        //是否队列私有化，false代表所有消费者都可以访问。 true代表只有第一次拥有它的消费者可以一致使用
        //是否自动深处，fa代表连接听到后不自动删除这个队列
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false, false, false, null);

        String message = "helloworld";
        //简单模式不指定交换机
        //队列名称
        //额外信息
        //byte信息
        channel.basicPublish("", RabbitConstant.QUEUE_HELLOWORLD, null, message.getBytes());
        channel.close();
        connection.close();
        System.out.println("message send successfully");
    }

}
