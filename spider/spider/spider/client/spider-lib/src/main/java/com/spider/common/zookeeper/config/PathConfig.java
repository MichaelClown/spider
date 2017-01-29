package com.spider.common.zookeeper.config;

import org.springframework.util.StringUtils;

/**
 * Created by jian.Michael on 2017/1/25.
 */
public class PathConfig {

    private static final String DEFAULTZKBASEPATH = "/com/spider/cfg/1.0.0";

    private String zkRootPath;

    public PathConfig() {

    }

    public PathConfig(String zkPath) {
        this.zkRootPath = StringUtils.hasText(zkPath) ? zkPath : DEFAULTZKBASEPATH;
    }

    public String getFullZkPath(String path) {
        return this.zkRootPath + path;
    }

    public String getZkRootPath() {
        return zkRootPath;
    }

    public void setZkRootPath(String zkRootPath) {
        this.zkRootPath = zkRootPath;
    }
}
