package com.spider.consumer.service;

import com.spider.consumer.repository.ConsumerWsRepository;
import com.spider.spider.consumer.response.AddressResponse;
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

    @Inject
    public void setConsumerWsRepository(ConsumerWsRepository consumerWsRepository) {
        this.consumerWsRepository = consumerWsRepository;
    }
}
