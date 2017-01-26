package com.spider.common.zookeeper.manager;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by jian.Michael on 2017/1/25.
 */
public class ZookeeperManager implements IzookeeperManager {

    private CuratorFramework curatorFramework;

    public ZookeeperManager(String zkAddress, String zkNameSpace, int sessionTimeOut, int connectTimeOut) {
        curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString(zkAddress)
                .namespace(zkNameSpace)
                .sessionTimeoutMs(sessionTimeOut)
                .connectionTimeoutMs(connectTimeOut)
                .retryPolicy(new RetryNTimes(2147483647, 1000))
                .build();
        try {
            curatorFramework.start();
            curatorFramework.blockUntilConnected();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(String path, boolean isTempNode) {
        try {
            if (isTempNode) {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
                return;
            } else {
                curatorFramework.create().creatingParentsIfNeeded().forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createWithData(String path, String data, boolean ifTemp) {
        try {
            this.createWithData(path, data.getBytes("UTF-8"), ifTemp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createWithData(String path, byte[] data, boolean ifTemp) {
        try {
            if (ifTemp) {
                this.curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data);
            } else {
                this.curatorFramework.create().creatingParentsIfNeeded().forPath(path, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(String path, String data, boolean inTransaction) {
        try {
            this.setData(path, data.getBytes("UTF-8"), inTransaction);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(String path, byte[] data, boolean inTransaction) {
        try {
            if (inTransaction) {
                this.curatorFramework.inTransaction().setData().forPath(path, data);
            } else {
                this.curatorFramework.setData().forPath(path, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readString(String path) throws Exception {
        return new String(this.readBytes(path), "UTF-8");
    }

    @Override
    public byte[] readBytes(String path) throws Exception {
        return this.curatorFramework.getData().forPath(path);
    }

    @Override
    public void delete(String path, boolean deleteChildren) {
        try {
            if (deleteChildren) {
                this.curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            } else {
                this.curatorFramework.delete().guaranteed().forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfExist(String path) {
        try {
            return this.curatorFramework.checkExists().forPath(path) != null;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public List<String> getChildrenNode(String path) {
        try {
            return this.curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {

    }
}
