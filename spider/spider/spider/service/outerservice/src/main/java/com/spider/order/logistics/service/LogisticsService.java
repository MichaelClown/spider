package com.spider.order.logistics.service;

import com.spider.common.SpiderBusinessException;
import com.spider.order.logistics.domain.LogisticsOrder;
import com.spider.order.logistics.domain.LogisticsOrderMessage;
import com.spider.order.logistics.repository.LogisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.comparator.BooleanComparator;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/4/4.
 */
@Service
public class LogisticsService {

    private Logger logger = LoggerFactory.getLogger(LogisticsService.class);

    private LogisticsRepository logisticsRepository;

    @Transactional
    public void processMessage(LogisticsOrderMessage orderMessage) {
        LogisticsOrder logisticsOrder = logisticsRepository.getLogisticsOrder(orderMessage.getOrderId());
        if (logisticsOrder == null || logisticsOrder.getOrderId() == null) {
            logger.warn("update status for {}, but doesn't exists it in spider system", orderMessage);
            return;
        }

        // 判断状态变更消息的状态时间是否晚于现有状态变更时间
        if(logisticsOrder.getUpdateDate().after(orderMessage.getUpdateDate())) {
            logger.warn("update status for {}, but the status date is before of status date in spider system", orderMessage);
            return;
        }

        // 更新状态时间
        Boolean result = logisticsRepository.updateStatueOfLogistics(orderMessage);
        if (!result) {
            logger.warn("update status for {} failed", orderMessage);
            throw new SpiderBusinessException("update status for {} failed", orderMessage.toString());
        }

        // 添加物流变更记录
        logisticsRepository.addLogisticsRecord(orderMessage);
    }

    @Inject
    public void setLogisticsRepository(LogisticsRepository logisticsRepository) {
        this.logisticsRepository = logisticsRepository;
    }
}
