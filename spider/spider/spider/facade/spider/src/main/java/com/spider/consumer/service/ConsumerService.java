package com.spider.consumer.service;

import com.spider.consumer.repository.ConsumerRepository;
import com.spider.spider.consumer.response.AddressResponse;
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

    @Inject
    public void setConsumerRepository(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }
}
