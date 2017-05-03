package com.spider.logistics.service;

import com.spider.logistics.repository.LogisticsRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by drcdnb20 on 17-4-3.
 */
@Service
public class LogisticsService {

    private LogisticsRepository logisticsRepository;

    public Boolean sign(String logisticsOrderId, Long customerId) {
        return logisticsRepository.sign(logisticsOrderId, customerId);
    }

    @Inject
    public void setLogisticsRepository(LogisticsRepository logisticsRepository) {
        this.logisticsRepository = logisticsRepository;
    }
}
