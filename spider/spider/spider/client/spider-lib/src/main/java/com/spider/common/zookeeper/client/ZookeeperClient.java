package com.spider.common.zookeeper.client;

import com.spider.common.zookeeper.manager.PathConfig;
import com.spider.common.zookeeper.manager.ZookeeperManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jian.Michael on 2017/1/25.
 */
public class ZookeeperClient {

    private static final int SESSION_TIME_OUT = 5000;
    private static final int CONNECT_TIME_OUT = 5000;

    protected ZookeeperManager zookeeperManager;

    private PathConfig pathConfig;

    public final Map<String, Map<String, String>> nodeMap = new ConcurrentHashMap<>();

    public ZookeeperClient(String zkAddress, String zkNameSpace) {
        zookeeperManager = new ZookeeperManager(zkAddress, zkNameSpace, SESSION_TIME_OUT, CONNECT_TIME_OUT);
        pathConfig = new PathConfig(zkNameSpace);
    }

    public void interator(String domainSpace, String zkPath) {
        this.loanNodes(domainSpace, zkPath);
    }

    public void loanNodes(String domainSpace, String zkPath) {
        try {
            List<String> childrenNode = this.zookeeperManager.getChildrenNode(pathConfig.getFullZkPath(zkPath));
            if (!CollectionUtils.isEmpty(childrenNode)) {
                Iterator iterator = childrenNode.iterator();
                while (iterator.hasNext()) {
                    String childPath = (String) iterator.next();
                    String childRelativePath = zkPath + "/" + childPath;
                    String childValue = this.zookeeperManager.readString(pathConfig.getFullZkPath(childRelativePath));
                    if (StringUtils.hasText(childValue)) {
                        this.put(domainSpace, childRelativePath, childValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(String domainSpace, String nodePath, String value) {
        Object nameSpaceMap = (Map) nodeMap.get(domainSpace);
        if (nameSpaceMap == null) {
            nameSpaceMap = new ConcurrentHashMap<>();
            this.nodeMap.put(domainSpace, (Map<String, String>) nameSpaceMap);
        }
        ((Map<String, String>) nameSpaceMap).put(nodePath, value);
    }

    public String get(String domainSpace, String nodePath) {
        Map<String, String> nameSpaceMap = this.nodeMap.get(domainSpace);
        return (nameSpaceMap == null || nameSpaceMap.isEmpty()) ? null : nameSpaceMap.get(nodePath);
    }





}
