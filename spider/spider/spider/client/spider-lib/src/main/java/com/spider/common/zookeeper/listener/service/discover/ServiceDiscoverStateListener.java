package com.spider.common.zookeeper.listener.service.discover;

import com.spider.common.zookeeper.manager.AbstractZookeeperFeature.IServiceClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public class ServiceDiscoverStateListener implements ConnectionStateListener {

    private final IServiceClient serviceClient;

    public ServiceDiscoverStateListener(IServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        switch (connectionState) {
            case LOST:
            case RECONNECTED:
                //失去连接、重新连接，重新发现服务
                while (true) {
                    try {
                        Thread.sleep(1000L);
                        if (curatorFramework.getZookeeperClient().isConnected()) {
                            this.serviceClient.discoverService(true);
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

}
