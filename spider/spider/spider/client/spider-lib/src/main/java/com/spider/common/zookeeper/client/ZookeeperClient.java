package com.spider.common.zookeeper.client;

import com.spider.common.zookeeper.listener.ConfigPathChildrenCacheListener;
import com.spider.common.zookeeper.manager.AbstractZookeeperFeature;
import com.spider.common.zookeeper.manager.AbstractZookeeperFeature.IZookeeperClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jian.Michael on 2017/1/25.
 */
public class ZookeeperClient extends AbstractZookeeperFeature implements IZookeeperClient {

    public final Map<String, Map<String, String>> nodeMap = new ConcurrentHashMap<>();

    public ZookeeperClient(String zkAddress, String zkNameSpace, String serviceDiscoverPath) {
        super(zkAddress, zkNameSpace, serviceDiscoverPath);
    }

    @Override
    public void iterator(String domainSpace, String zkPath) {
        ConfigPathChildrenCacheListener listener = new ConfigPathChildrenCacheListener(domainSpace, pathConfig.getZkRootPath(), this);
        this.zookeeperManager.addPathChildrenListener(pathConfig.getFullZkPath(zkPath), listener);
        this.loanNodes(domainSpace, zkPath);
    }

    private void loanNodes(String domainSpace, String zkPath) {
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

    @Override
    public void put(String domainSpace, String nodePath, String value) {
        Object nameSpaceMap = (Map) nodeMap.get(domainSpace);
        if (nameSpaceMap == null) {
            nameSpaceMap = new ConcurrentHashMap<>();
            this.nodeMap.put(domainSpace, (Map<String, String>) nameSpaceMap);
        }
        ((Map<String, String>) nameSpaceMap).put(nodePath, value);
    }

    @Override
    public String get(String domainSpace, String nodePath) {
        Map<String, String> nameSpaceMap = this.nodeMap.get(domainSpace);
        return (nameSpaceMap == null || nameSpaceMap.isEmpty()) ? null : nameSpaceMap.get(nodePath);
    }

    @Override
    public void remove(String domainSpace, String nodePath) {
        Object nameSpaceMap = (Map) nodeMap.get(domainSpace);
        if (nameSpaceMap == null) {
            nameSpaceMap = new ConcurrentHashMap<>();
            this.nodeMap.put(domainSpace, (Map<String, String>) nameSpaceMap);
        }
        ((Map<String, String>) nameSpaceMap).remove(nodePath);
    }





}
