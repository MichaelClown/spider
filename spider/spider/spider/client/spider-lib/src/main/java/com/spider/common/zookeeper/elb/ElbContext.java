package com.spider.common.zookeeper.elb;

import com.spider.common.zookeeper.domain.ServiceDetail;
import com.spider.common.zookeeper.domain.ServiceGroup;
import com.spider.common.zookeeper.elb.strategy.LbStrategy;
import com.spider.common.zookeeper.elb.strategy.RandomStrategy;
import com.spider.common.zookeeper.elb.strategy.RobinStrategy;

import java.util.Map;

/**
 * Created by jian.Michael on 2017/4/9.
 */
public class ElbContext {

    private final LbStrategy lbStrategy;

    public ElbContext(ELB elb) {
        switch (elb) {
            case ROBIN:
                this.lbStrategy = new RobinStrategy();
                break;
            case RANDOM:
                this.lbStrategy = new RandomStrategy();
                break;
            default:
                this.lbStrategy = new RobinStrategy();
                break;
        }
    }

    public ServiceDetail loadBalance(String serviceName, Map<String, ServiceGroup> onlionService) throws InterruptedException {
        return lbStrategy.loadBalance(serviceName, onlionService);
    }

    public static enum ELB {
        ROBIN,
        RANDOM
    }
}
