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

    @Inject
    public void setConsumerWsRepository(ConsumerWsRepository consumerWsRepository) {
        this.consumerWsRepository = consumerWsRepository;
    }
}
