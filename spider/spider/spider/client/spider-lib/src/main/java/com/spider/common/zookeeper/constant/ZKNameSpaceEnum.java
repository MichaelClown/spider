package com.spider.common.zookeeper.constant;

/**
 * Created by jian.Michael on 2017/1/26.
 */
public enum  ZKNameSpaceEnum {

    RABBITMQ("rabbitmq", "/common/rabbitmq"),
    DATABASE("database", "/common/database"),
    QUEUE("queue", "/logistics/queue");

    private ZKNameSpaceEnum(String nameSpace, String zkPath) {
        this.nameSpace = nameSpace;
        this.zkPath = zkPath;
    }

    private String nameSpace;

    private String zkPath;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getZkPath() {
        return zkPath;
    }

    public void setZkPath(String zkPath) {
        this.zkPath = zkPath;
    }
}
