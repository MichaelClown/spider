package com.spider.common.zookeeper.elb.strategy;

import com.spider.common.zookeeper.domain.ServiceDetail;
import com.spider.common.zookeeper.domain.ServiceGroup;

import java.util.Map;

/**
 * Created by jian.Michael on 2017/4/9.
 */
public interface LbStrategy {

    ServiceDetail loadBalance(String var1, Map<String, ServiceGroup> var2) throws InterruptedException;

}
