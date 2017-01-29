package com.spider.common.zookeeper.client;

import com.spider.common.zookeeper.constant.ServiceEnum;
import com.spider.common.zookeeper.manager.AbstractZookeeperFeature;
import com.spider.common.zookeeper.manager.AbstractZookeeperFeature.IServiceClient;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public class ServiceClient extends AbstractZookeeperFeature implements IServiceClient {

    private ServiceEnum serviceType;

    public ServiceClient(String zkAddress, String zkNameSpace) {
        super(zkAddress, zkNameSpace);
    }


    @Override
    public void registService() {

    }

    @Override
    public void discoverService(ServiceEnum var1, String... var2) {

    }

    @Override
    public void startListener() {

    }

    @Override
    public boolean checkHealth(String var1) {
        return false;
    }
}
