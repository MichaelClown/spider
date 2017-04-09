package com.spider.common.zookeeper.elb.strategy;

import com.spider.common.zookeeper.domain.ServiceDetail;
import com.spider.common.zookeeper.domain.ServiceGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jian.Michael on 2017/4/9.
 */
public class RobinStrategy implements LbStrategy {

    private Logger logger = LoggerFactory.getLogger(RobinStrategy.class);

    public RobinStrategy() {

    }

    @Override
    public ServiceDetail loadBalance(String path, Map<String, ServiceGroup> serviceGroupMap) throws InterruptedException {
        return this.loadBlance(path, serviceGroupMap, 0);
    }

    private ServiceDetail loadBlance(String path, Map<String, ServiceGroup> serviceGroupMap, int callCounter) throws InterruptedException {
        if (callCounter > 0) {
            Thread.sleep(1000L);
        }

        if (callCounter >= 3) {
            throw new RuntimeException("Service[" + path + "] is lost...");
        } else {
            ServiceGroup serviceGroup = serviceGroupMap.get(path);
            if (serviceGroup.isEmpty()) {
                throw new RuntimeException("Service[" + path + "] is empty...");
            } else {
                int length = serviceGroup.size();
                int currentCounter = serviceGroup.getVisitCounter().incrementAndGet();
                int currentIndex = currentCounter % length;
                try {
                    int index = 0;
                    return this.getService(serviceGroup, index, currentIndex);
                } catch (Exception e) {
                    logger.warn("Service[{}] load balance failed, try again for the {} times", path, callCounter + 1);
                    return this.loadBlance(path, serviceGroupMap, callCounter + 1);
                }

            }

        }
    }

    private ServiceDetail getService(ServiceGroup serviceGroup, int index, int currentIndex) {
        Iterator iterator = serviceGroup.getOnlineServiceMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ServiceDetail> entry = (Map.Entry<String, ServiceDetail>) iterator.next();
            if (index++ == currentIndex) {
                return entry.getValue();
            }
        }
        return null;
    }

}
