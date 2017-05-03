package com.spider.logistics.repository;

import com.spider.common.api.EndPoint;
import com.spider.common.api.client.SpiderApiClient;
import com.spider.common.api.client.endpoint.EndPointFactory;
import com.spider.common.api.client.endpoint.SpiderEndPointBuilder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by drcdnb20 on 17-4-3.
 */
@Repository
public class LogisticsRepository {

    private SpiderApiClient spiderApiClient;

    private EndPointFactory factory;

    public Boolean sign(String logisticsOrderId, Long customerId) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(Boolean.class).factory(factory)
                    .endpoint(EndPoint.INNER).action("/logistics/sign/%s/%s").arguments(logisticsOrderId, customerId.toString()));
    }

    @Inject
    public void setFactory(EndPointFactory factory) {
        this.factory = factory;
    }

    @Inject
    public void setSpiderApiClient(SpiderApiClient spiderApiClient) {
        this.spiderApiClient = spiderApiClient;
    }


}
