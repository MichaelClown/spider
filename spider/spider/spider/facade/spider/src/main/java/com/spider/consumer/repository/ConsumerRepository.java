package com.spider.consumer.repository;

import com.spider.common.api.EndPoint;
import com.spider.common.api.client.SpiderApiClient;
import com.spider.common.api.client.endpoint.EndPointFactory;
import com.spider.common.api.client.endpoint.SpiderEndPointBuilder;
import com.spider.spider.consumer.response.AddressResponse;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jian.Michael on 2017/5/3.
 */
@Repository
public class ConsumerRepository {

    private SpiderApiClient spiderApiClient;

    private EndPointFactory factory;

    public List<AddressResponse> getAddressListOfUser(Long customerId) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(List.class).factory(factory).endpoint(EndPoint.INNER)
                .elementTypes(AddressResponse.class).action("/addresslist/$s").arguments(customerId.toString()));
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
