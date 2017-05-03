package com.spider.logistics.sevice;

import com.spider.logistics.repository.LogisticsWsRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by drcdnb20 on 17-4-3.
 */
@Service
public class LogisticsWsService {

    private LogisticsWsRepository logisticsWsRepository;

    public Boolean sign(Long logisticsOrderId, Long customerId) {
        return logisticsWsRepository.sign(logisticsOrderId, customerId);
    }

    @Inject
    public void setLogisticsWsRepository(LogisticsWsRepository logisticsWsRepository) {
        this.logisticsWsRepository = logisticsWsRepository;
    }
}
