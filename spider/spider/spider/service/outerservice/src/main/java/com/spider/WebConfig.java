package com.spider;

import com.spider.common.amqp.constant.QueueNameEnum;
import com.spider.common.zookeeper.client.ServiceClient;
import com.spider.common.zookeeper.client.ZookeeperClient;
import com.spider.common.zookeeper.config.ServiceConfig;
import com.spider.common.zookeeper.constant.NameSpaceEnum;
import com.spider.integrate.amqp.handler.TestMessageHandler;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by jian.Michael on 2017/1/26.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebConfig.class)
public class WebConfig extends DbConfig {

    final String zkAddress = "182.254.131.63:2181";
    final String zkServiceNameSpace = "/com/spider/cfg/1.0.0/service";
    final String zkNameSpace = "/com/spider/cfg/1.0.0";
    final String serviceDiscoverPath = zkServiceNameSpace + System.getProperty("com.spider.service.discover", "/spider-service");

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ZookeeperClient zookeeperClient() {
        ZookeeperClient zookeeperClient = new ZookeeperClient(zkAddress, zkNameSpace, serviceDiscoverPath);
        zookeeperClient.iterator(NameSpaceEnum.RABBITMQ.getNameSpace(), NameSpaceEnum.RABBITMQ.getZkPath());
        zookeeperClient.iterator(NameSpaceEnum.DATABASE.getNameSpace(), NameSpaceEnum.DATABASE.getZkPath());
        zookeeperClient.iterator(NameSpaceEnum.QUEUE.getNameSpace(), NameSpaceEnum.QUEUE.getZkPath());
        return zookeeperClient;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ServiceClient serviceClient() {
        ServiceConfig serviceConfig = new ServiceConfig()
                .serviceName(ServiceConfig.ServiceNameEnum.OUTER)
                .serviceTypeEnum(ServiceConfig.ServiceTypeNum.PROVIDER);
        ServiceClient serviceClient = new ServiceClient(zkAddress, zkNameSpace, serviceDiscoverPath, serviceConfig);
        serviceClient.startListener();
        serviceClient.registService();
        return serviceClient;
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

    @Bean
    @Qualifier("testMessageListenerContainer")
    public SimpleMessageListenerContainer testMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        String queueName = getZKValue(NameSpaceEnum.QUEUE.getNameSpace(), QueueNameEnum.TEST_QUEUE.getQueueZkPath(), QueueNameEnum.TEST_QUEUE.getDefaultQueueName());
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(queueName);
        container.setMessageListener(testMessageHandler());
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return container;
    }

    @Bean
    public TestMessageHandler testMessageHandler() {
        return new TestMessageHandler();
    }

    private String getZKValue(String nameSpace, String zkPath, String defaultValue) {
        String value = zookeeperClient().get(nameSpace, zkPath);
        return StringUtils.hasText(value) ? value : defaultValue;
    }
}
