package com.spider.consumer.service;

import com.spider.consumer.repository.ConsumerWsRepository;
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
public class ConsumerWsService {

    private ConsumerWsRepository consumerWsRepository;

    public List<AddressResponse> getAddressListOfUser(Long customerId) {
        return consumerWsRepository.getAddressListOfUser(customerId);
    }

    public AddressResponse getAddress(Long customerId, Long addressId) {
        return consumerWsRepository.getAddress(customerId, addressId);
    }

    public Boolean deleteAddress(Long customerId, Long addressId) {
        return consumerWsRepository.deleteAddress(customerId, addressId);
    }

    public AddressResponse saveAddress(AddressResponse addressResponse) {
        if (addressResponse != null && addressResponse.getAddressId() != null) {
            Boolean result = consumerWsRepository.updateAddress(addressResponse);
            return result ? addressResponse : new AddressResponse();
        } else {
            return consumerWsRepository.insertAddress(addressResponse);
        }
    }

    public List<OrderResponse> getOrderListOfUser(Long customerId) {
        return consumerWsRepository.getOrderListOfUser(customerId);
    }

    public OrderResponse getOrderDetailByOrderId(Long orderId) {
        return consumerWsRepository.getOrderDetailByOrderId(orderId);
    }

    public List<OrderRecordItem> getOrderRecordList(Long orderId) {
        return consumerWsRepository.getOrderRecordList(orderId);
    }

    @Inject
    public void setConsumerWsRepository(ConsumerWsRepository consumerWsRepository) {
        this.consumerWsRepository = consumerWsRepository;
    }
}
