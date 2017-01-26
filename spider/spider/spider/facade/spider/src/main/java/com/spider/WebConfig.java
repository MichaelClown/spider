package com.spider;

import com.spider.common.zookeeper.client.ZookeeperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jian.Michael on 2017/1/25.
 */
@Configuration
public class WebConfig {

    private final String zkAddress = "192.168.1.3:2181";
    private final String zkNameSpace = "/com/spider/cfg/1.0.0";


    @Bean
    public ZookeeperClient zookeeperClient() {
        ZookeeperClient zookeeperClient = new ZookeeperClient(zkAddress, zkNameSpace);
        zookeeperClient.interator("rabbitmq", "/common/rabbitmq");
        zookeeperClient.interator("database", "/common/database");
        return zookeeperClient;
    }


}