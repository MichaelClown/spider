package com.spider;

 import com.spider.common.api.Apis;
import com.spider.common.zookeeper.client.ServiceClient;
import com.spider.common.zookeeper.client.ZookeeperClient;
import com.spider.common.zookeeper.config.ServiceConfig;
import com.spider.common.zookeeper.constant.NameSpaceEnum;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by jian.Michael on 2017/1/25.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebConfig.class)
public class WebConfig {

    private final String zkAddress = "182.254.131.63:2181";
    private final String zkNameSpace = "/com/spider/cfg/1.0.0";
    private final String zkServiceNameSpace = "/com/spider/cfg/1.0.0/service";
    final String serviceDiscoverPath = zkServiceNameSpace + System.getProperty("com.spider.service.discover", "/spider-service");


    @Bean
    public ZookeeperClient zookeeperClient() {
        ZookeeperClient zookeeperClient = new ZookeeperClient(zkAddress, zkNameSpace, serviceDiscoverPath);
        zookeeperClient.iterator(NameSpaceEnum.RABBITMQ.getNameSpace(), NameSpaceEnum.RABBITMQ.getZkPath());
        zookeeperClient.iterator(NameSpaceEnum.DATABASE.getNameSpace(), NameSpaceEnum.DATABASE.getZkPath());
        return zookeeperClient;
    }

    @Bean
    public ServiceClient serviceClient() {
        ServiceConfig serviceConfig = new ServiceConfig()
                .serviceTypeEnum(ServiceConfig.ServiceTypeNum.CONSUMER);
        ServiceClient serviceClient = new ServiceClient(zkAddress, zkNameSpace, serviceDiscoverPath, serviceConfig);
        serviceClient.startListener();
        serviceClient.discoverService(false);
        return serviceClient;
    }

    @Bean
    public Apis apis() {
        Apis apis = null;
        return apis;
    }


    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionFactory connectionFactory() {
        ZookeeperClient zkClient = zookeeperClient();
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/address"));
        factory.setPort(Integer.parseInt(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/port")));
        factory.setVirtualHost(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/vhosts"));
        factory.setUsername(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/username"));
        factory.setPassword(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/password"));
        return factory;
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        return new RabbitTemplate(connectionFactory());
    }


}
