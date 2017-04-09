package com.spider.common.zookeeper.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jian.Michael on 2017/1/30.
 */
public class ServiceGroup {

    private static final Lock LOCK = new ReentrantLock();

    private final Map<String, ServiceDetail> onlineServiceMap = new ConcurrentHashMap<>();

    private final Map<String, ServiceDetail> offlineServiceMap = new ConcurrentHashMap<>();

    private final AtomicInteger visitCounter = new AtomicInteger(0);

    private String serviceName;

    public ServiceGroup(String serviceName) {
        this.serviceName = serviceName;
    }

    public void put(String serviceKey, ServiceDetail serviceDetail) {
        Lock lock = LOCK;
        try {
            lock.lock();
            if (this.offlineServiceMap.containsKey(serviceKey)) {
                this.offlineServiceMap.remove(serviceKey);
            }
            this.onlineServiceMap.put(serviceKey, serviceDetail);
        } finally {
            lock.unlock();
        }
    }

    public void remove(String serviceKey) {
        Lock lock = LOCK;
        try {
            lock.lock();
            ServiceDetail serviceDetail = null;
            if (this.onlineServiceMap.containsKey(serviceKey)) {
                serviceDetail = this.onlineServiceMap.remove(serviceKey);
            }
            this.offlineServiceMap.put(serviceKey, serviceDetail);
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return this.onlineServiceMap.isEmpty();
    }

    public Integer size() {
        return this.onlineServiceMap.size();
    }

    public AtomicInteger getVisitCounter() {
        return visitCounter;
    }

    public Map<String, ServiceDetail> getOnlineServiceMap() {
        return this.onlineServiceMap;
    }
}
