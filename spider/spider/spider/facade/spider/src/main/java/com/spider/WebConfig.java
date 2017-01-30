package com.spider;

import com.spider.common.zookeeper.client.ServiceClient;
import com.spider.common.zookeeper.client.ZookeeperClient;
import com.spider.common.zookeeper.config.ServiceConfig;
import com.spider.common.zookeeper.constant.NameSpaceEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jian.Michael on 2017/1/25.
 */
@Configuration
public class WebConfig {

    private final String zkAddress = "192.168.1.5:2181";
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


}
