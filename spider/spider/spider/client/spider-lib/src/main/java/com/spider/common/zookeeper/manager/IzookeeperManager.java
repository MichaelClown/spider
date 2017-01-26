package com.spider.common.zookeeper.manager;

/**
 * Created by jian.Michael on 2017/1/25.
 */
public interface IzookeeperManager {

    void create(String path, boolean ifTemp);

    void createWithData(String path, String data, boolean ifTemp);

    void createWithData(String path, byte[] data, boolean ifTemp);

    void setData(String path, String data, boolean inTransaction);

    void setData(String path, byte[] data, boolean inTransaction);

    String readString(String path) throws Exception;

    byte[] readBytes(String path) throws Exception;

    void delete(String path, boolean deleteChildren);

    boolean checkIfExist(String path);

    void close();

}
