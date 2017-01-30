package com.spider.common.zookeeper.config;

import org.springframework.util.StringUtils;

/**
 * Created by jian.Michael on 2017/1/25.
 */
public class PathConfig {

    private static final String DEFAULTZKBASEPATH = "/com/spider/cfg/1.0.0";
    private static final String DEFAULTSERVICEDISCOVERPATH = DEFAULTZKBASEPATH + "/service" + "/spider-service";

    private String zkRootPath;

    private String serviceDiscoverPath;

    public PathConfig() {

    }

    public PathConfig(String zkPath, String serviceDiscoverPath) {
        this.zkRootPath = StringUtils.hasText(zkPath) ? zkPath : DEFAULTZKBASEPATH;
        this.serviceDiscoverPath = StringUtils.hasText(serviceDiscoverPath) ? serviceDiscoverPath : DEFAULTSERVICEDISCOVERPATH;
    }

    public String getFullZkPath(String path) {
        return this.zkRootPath + path;
    }

    public String getFullDiscoverPath(String path) {
        return this.serviceDiscoverPath + path;
    }

    public String getZkRootPath() {
        return zkRootPath;
    }

    public void setZkRootPath(String zkRootPath) {
        this.zkRootPath = zkRootPath;
    }

    public String getServiceDiscoverPath() {
        return serviceDiscoverPath;
    }

    public void setServiceDiscoverPath(String serviceDiscoverPath) {
        this.serviceDiscoverPath = serviceDiscoverPath;
    }
}
