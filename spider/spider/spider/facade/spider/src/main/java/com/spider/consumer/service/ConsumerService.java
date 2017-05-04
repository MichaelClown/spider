package com.spider.consumer.service;

import com.spider.consumer.repository.ConsumerRepository;
import com.spider.spider.consumer.response.AddressResponse;
import com.spider.spider.order.OrderRecordItem;
import com.spider.spider.order.OrderResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@Service
public class ConsumerService {

    private ConsumerRepository consumerRepository;

    public List<AddressResponse> getAddressListOfUser(Long customerId) {
        return consumerRepository.getAddressListOfUser(customerId);
    }

    public AddressResponse getAddress(Long customerId, Long addressId) {
        return consumerRepository.getAddress(customerId, addressId);
    }

    public Boolean deleteAddress(Long customerId, Long addressId) {
        return consumerRepository.deleteAddress(customerId, addressId);
    }

    public AddressResponse saveAddress(AddressResponse addressResponse) {
        return consumerRepository.saveAddress(addressResponse);
    }

    public List<OrderResponse> getOrderListOfUser(Long customerId) {
        return consumerRepository.getOrderListOfUser(customerId);
    }

    public OrderResponse getOrderDetailByOrderId(Long orderId) {
        return consumerRepository.getOrderDetailByOrderId(orderId);
    }

    public List<OrderRecordItem> getOrderRecordList(Long orderId) {
        return consumerRepository.getOrderRecordList(orderId);
    }

    @Inject
    public void setConsumerRepository(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }
}
