package com.spider.consumer.repository;

import com.alibaba.fastjson.JSON;
import com.spider.common.api.EndPoint;
import com.spider.common.api.client.SpiderApiClient;
import com.spider.common.api.client.endpoint.EndPointFactory;
import com.spider.common.api.client.endpoint.SpiderEndPointBuilder;
import com.spider.spider.consumer.response.AddressResponse;
import com.spider.spider.order.OrderRecordItem;
import com.spider.spider.order.OrderResponse;
import com.sun.org.apache.xpath.internal.operations.Or;
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
                .elementTypes(AddressResponse.class).action("/addresslist/%s").arguments(customerId.toString()));
    }

    public AddressResponse getAddress(Long customerId, Long addressId) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(AddressResponse.class).factory(factory)
                .endpoint(EndPoint.INNER).action("/address/%s/consumer/%s").arguments(customerId.toString(), addressId.toString()));
    }

    public Boolean deleteAddress(Long customerId, Long addressId) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(Boolean.class).factory(factory)
                .endpoint(EndPoint.INNER).action("/address/delete/%s/%s").arguments(customerId.toString(), addressId.toString()));
    }

    public AddressResponse saveAddress(AddressResponse addressResponse) {
        return spiderApiClient.post(SpiderEndPointBuilder.create(AddressResponse.class).factory(factory)
                .endpoint(EndPoint.INNER).action("/address/save").body(JSON.toJSONString(addressResponse)));
    }

    public List<OrderResponse> getOrderListOfUser(Long customerId) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(List.class).elementTypes(OrderResponse.class).factory(factory)
                        .endpoint(EndPoint.INNER).action("/orderlist/%s").arguments(customerId.toString()));
    }

    public OrderResponse getOrderDetailByOrderId(Long orderId) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(OrderResponse.class).factory(factory)
                        .endpoint(EndPoint.INNER).action("/orderdetail/%s").arguments(orderId.toString()));
    }

    public List<OrderRecordItem> getOrderRecordList(Long orderId) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(List.class).elementTypes(OrderRecordItem.class).factory(factory)
                        .endpoint(EndPoint.INNER).action("/orderrecord/%s").arguments(orderId.toString()));
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
