package com.spider.common.zookeeper.manager;

import com.spider.common.zookeeper.config.PathConfig;
import com.spider.common.zookeeper.constant.ServiceEnum;

import java.util.List;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public abstract class AbstractZookeeperFeature {

    private static final int SESSION_TIME_OUT = 5000;
    private static final int CONNECT_TIME_OUT = 5000;

    protected IZookeeperManager zookeeperManager;

    protected PathConfig pathConfig;

    public AbstractZookeeperFeature(String zkAddress, String zkNameSpace) {
        this.zookeeperManager = new ZookeeperManager(zkAddress, zkNameSpace, SESSION_TIME_OUT, CONNECT_TIME_OUT);
        pathConfig = new PathConfig(zkNameSpace);
    }

    public interface IZookeeperManager {
        void create(String path, boolean ifTemp);

        void createWithData(String path, String data, boolean ifTemp);

        void createWithData(String path, byte[] data, boolean ifTemp);

        void setData(String path, String data, boolean inTransaction);

        void setData(String path, byte[] data, boolean inTransaction);

        List<String> getChildrenNode(String path);

        String readString(String path) throws Exception;

        byte[] readBytes(String path) throws Exception;

        void delete(String path, boolean deleteChildren);

        boolean checkIfExist(String path);

        void close();
    }

    public interface IZookeeperClient {

        void iterator(String domainPath, String zkPath);

        String get(String domainPath, String zkPath);
    }

    public interface IServiceClient {

        void registService();

        void discoverService(ServiceEnum var1, String... var2);

        void startListener();

        boolean checkHealth(String var1);
    }
}
