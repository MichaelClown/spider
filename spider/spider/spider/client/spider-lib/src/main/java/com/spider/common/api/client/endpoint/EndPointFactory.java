package com.spider.common.api.client.endpoint;

import com.spider.common.zookeeper.client.ServiceClient;
import com.spider.common.zookeeper.client.ZookeeperClient;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 服务端点 factory
 * Created by jian.Michael on 2017/3/12.
 */
public class EndPointFactory {

    private ServiceClient serviceClient;

    private ZookeeperClient zookeeperClient;

    private ServiceGroups serviceGroups;

    private final Map<String, Map<String, EndPoint>> concurrentMap = new ConcurrentHashMap<>();

    public EndPointFactory() {

    }

    @Autowired
    public void setServiceClient(ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Autowired
    public void setZookeeperClient(ZookeeperClient zookeeperClient) {
        this.zookeeperClient = zookeeperClient;
    }

    @Autowired
    public void setServiceGroups(ServiceGroups serviceGroups) {
        this.serviceGroups = serviceGroups;
    }

    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    public ZookeeperClient getZookeeperClient() {
        return zookeeperClient;
    }

    public ServiceGroups getServiceGroups() {
        return serviceGroups;
    }

    public Map<String, EndPoint> getDefaultServiceGroups() {
        return concurrentMap.get("SERVICE");
    }

    public void initinalize() {
        if (!CollectionUtils.isEmpty(serviceGroups.getServiceGroups())) {
            for (ServiceGroup serviceGroup : serviceGroups.getServiceGroups()) {
                ConcurrentHashMap endPointMap = new ConcurrentHashMap();
                List<Service> services = serviceGroup.getServices();
                if (!CollectionUtils.isEmpty(services)) {
                    for (Service service : services) {
                        endPointMap.put(service.getServiceName(), service.getEndPoint());
                    }
                }
                this.concurrentMap.put(serviceGroup.getGroupName(), endPointMap);
            }
        }
    }

    public Map<String, Map<String, EndPoint>> getConcurrentMap() {
        return concurrentMap;
    }
}
