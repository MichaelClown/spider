package com.spider.common.zookeeper.listener.service.register;

import com.spider.common.zookeeper.manager.AbstractZookeeperFeature.IServiceClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public class ServiceRegistStateListener implements ConnectionStateListener {

    private final IServiceClient serviceClient;

    public ServiceRegistStateListener(IServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        switch (connectionState) {
            case LOST:
                //失去连接，重新尝试连接
                while (true) {
                    try {
                        Thread.sleep(1000L);
                        if (curatorFramework.getZookeeperClient().isConnected()) {
                            this.serviceClient.registService();
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RECONNECTED:
                //重新连接，重新注册服务
                while (true) {
                    try {
                        Thread.sleep(1000L);
                        if (curatorFramework.getZookeeperClient().isConnected()) {
                            this.serviceClient.registService();
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                //其余事件不予理睬
                break;
        }
    }

}
