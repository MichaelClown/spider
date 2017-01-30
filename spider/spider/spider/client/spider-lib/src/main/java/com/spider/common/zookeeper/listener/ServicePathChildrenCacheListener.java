package com.spider.common.zookeeper.listener;

import com.spider.common.zookeeper.domain.ServiceDetail;
import com.spider.common.zookeeper.domain.ServiceGroup;
import com.spider.common.zookeeper.manager.AbstractZookeeperFeature.IServiceClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jian.Michael on 2017/1/30.
 */
public class ServicePathChildrenCacheListener implements PathChildrenCacheListener {

    private final String serviceName;

    private final String servicePath;

    private final IServiceClient serviceClient;

    public ServicePathChildrenCacheListener(String serviceName, String servicePath, IServiceClient serviceClient) {
        this.serviceClient = serviceClient;
        this.serviceName = serviceName;
        this.servicePath = servicePath;
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
        ChildData childData = pathChildrenCacheEvent.getData();
        if (childData.getData() != null) {
            String data = new String(childData.getData(), "UTF-8");
            String path = childData.getPath();
            String serviceChildName = this.getRelativePath(path);
            if (StringUtils.hasText(serviceChildName)) {
                ServiceGroup serviceGroup = this.serviceClient.getServiceGroupContainer().get(this.serviceName);
                handlerEvent(serviceGroup, pathChildrenCacheEvent, data, serviceChildName);
            }

        }
    }

    private void handlerEvent(ServiceGroup serviceGroup, PathChildrenCacheEvent pathChildrenCacheEvent, String data, String serviceChildName) throws Exception {
        if (serviceGroup == null) {
            serviceGroup = new ServiceGroup(this.serviceName);
            this.serviceClient.getServiceGroupContainer().put(this.serviceName, serviceGroup);
        }

        switch (pathChildrenCacheEvent.getType()) {
            case CHILD_ADDED:
            case CHILD_UPDATED:
                ServiceDetail serviceDetail = ServiceDetail.parseZKValue(data);
                serviceGroup.put(serviceChildName, serviceDetail);
                break;
            case CHILD_REMOVED:
                serviceGroup.remove(serviceChildName);
                break;
            default:
                break;
        }
    }

    private String getRelativePath(String absolutePath) {
        Pattern pattern = Pattern.compile(this.servicePath);
        Matcher matcher = pattern.matcher(absolutePath);
        if (matcher.find()) {
            int length = matcher.end();
            return absolutePath.substring(length+1);
        } else {
            return null;
        }
    }


}
