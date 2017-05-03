package com.spider.logistics.repository;

import com.spider.common.database.JDBCAccessUtil;
import com.spider.spider.logistics.LogisticsOrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by drcdnb20 on 17-4-3.
 */
@Repository
public class LogisticsWsRepository {

    private Logger logger = LoggerFactory.getLogger(LogisticsWsRepository.class);

    private JDBCAccessUtil jdbcAccessUtil;

    public Boolean sign(Long logisticsOrderId, Long customerId) {
        Integer result = jdbcAccessUtil.execute("logistics.SQL_UPDATE_LOGISTICS_ORDER_WITH_NEWSTATUS", LogisticsOrderStatus.ALREADY_SIGN.name(), logisticsOrderId, customerId);
        return result > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Inject
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }



}
