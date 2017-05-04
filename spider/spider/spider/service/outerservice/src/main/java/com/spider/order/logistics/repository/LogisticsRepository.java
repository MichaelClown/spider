package com.spider.order.logistics.repository;

import com.spider.common.SpiderBusinessException;
import com.spider.common.database.JDBCAccessUtil;
import com.spider.order.logistics.domain.LogisticsOrder;
import com.spider.order.logistics.domain.LogisticsOrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/4/4.
 */
@Repository
public class LogisticsRepository {

    private JDBCAccessUtil jdbcAccessUtil;

    private Logger logger = LoggerFactory.getLogger(LogisticsRepository.class);

    public LogisticsOrder getLogisticsOrder(Long orderId) {
        return jdbcAccessUtil.queryForObject("logistics.SQL_SELECT_ORDER_DETAIL", (resultSet, i) -> {
            LogisticsOrder logisticsOrder = new LogisticsOrder();
            logisticsOrder.setOrderId(resultSet.getLong("logistics_order_id"));
            return logisticsOrder;
        }, orderId);
    }

    public Boolean updateStatueOfLogistics(LogisticsOrderMessage logisticsOrderMessage) {
        Integer result = jdbcAccessUtil.execute("logistics.SQL_UPDATE_ORDER_STATUS", logisticsOrderMessage.getStatus(), logisticsOrderMessage.getOrderId());
        return result > 0 ? Boolean.TRUE :Boolean.FALSE;
    }

    public void addLogisticsRecord(LogisticsOrderMessage logisticsOrderMessage) {
        Integer result = jdbcAccessUtil.execute("logistics.SQL_INSERT_ORDER_RECORD", logisticsOrderMessage.getDetail(), logisticsOrderMessage.getOrderId());
        if (result == 0) {
            throw new SpiderBusinessException("Failed to add logistics change record");
        }
    }

    @Inject
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }
}
