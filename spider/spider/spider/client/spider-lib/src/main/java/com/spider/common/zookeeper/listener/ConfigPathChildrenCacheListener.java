package com.spider.common.zookeeper.listener;

import com.spider.common.zookeeper.manager.AbstractZookeeperFeature.IZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public class ConfigPathChildrenCacheListener implements PathChildrenCacheListener {

    private final String domainSpace;

    private final String zkRootPath;

    private final IZookeeperClient zookeeperClient;

    public ConfigPathChildrenCacheListener(String domainSpace, String zkRootPath, IZookeeperClient zookeeperClient) {
        this.domainSpace = domainSpace;
        this.zkRootPath = zkRootPath;
        this.zookeeperClient = zookeeperClient;
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {

        ChildData childData = pathChildrenCacheEvent.getData();

        if (null != childData) {
            String data = new String(childData.getData(), "UTF-8");
            String path = childData.getPath();
            String relativePath = this.getRelativePath(path);
            PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
            if (StringUtils.hasText(relativePath)) {
                switch (type) {
                    case CHILD_ADDED:
                    case CHILD_UPDATED:
                        this.zookeeperClient.put(domainSpace, relativePath, data);
                        break;
                    case CHILD_REMOVED:
                        this.zookeeperClient.remove(domainSpace, relativePath);
                        break;
                    default:
                        break;
                }
            }

        }
    }

    private String getRelativePath(String absolutePath) {
        Pattern pattern = Pattern.compile(this.zkRootPath);
        Matcher matcher = pattern.matcher(absolutePath);
        if (matcher.find()) {
            int length = matcher.end();
            return absolutePath.substring(length);
        } else {
            return null;
        }
    }

}
