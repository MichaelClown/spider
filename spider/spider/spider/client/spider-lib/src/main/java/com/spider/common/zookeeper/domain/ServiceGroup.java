package com.spider.common.zookeeper.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jian.Michael on 2017/1/30.
 */
public class ServiceGroup {

    private final Map<String, ServiceDetail> onlineServiceMap = new ConcurrentHashMap<>();

    private final Map<String, ServiceDetail> offlineServiceMap = new ConcurrentHashMap<>();

    private String serviceName;

    public ServiceGroup(String serviceName) {
        this.serviceName = serviceName;
    }

    public void put(String serviceKey, ServiceDetail serviceDetail) {
        if (this.offlineServiceMap.containsKey(serviceKey)) {
            this.offlineServiceMap.remove(serviceKey);
        }

        this.onlineServiceMap.put(serviceKey, serviceDetail);
    }

    public void remove(String serviceKey) {

        ServiceDetail serviceDetail = null;

        if (this.onlineServiceMap.containsKey(serviceKey)) {
            serviceDetail = this.onlineServiceMap.remove(serviceKey);
        }

        this.offlineServiceMap.put(serviceKey, serviceDetail);
    }

    public boolean isEmpty() {
        return this.onlineServiceMap.isEmpty();
    }

    public Integer size() {
        return this.onlineServiceMap.size();
    }

}
