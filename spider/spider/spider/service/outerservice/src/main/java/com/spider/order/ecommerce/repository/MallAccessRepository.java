package com.spider.order.ecommerce.repository;

import com.spider.common.database.JDBCAccessUtil;
import com.spider.order.ecommerce.domain.Commodity;
import com.spider.order.ecommerce.domain.MallOrderMessage;
import com.spider.order.ecommerce.domain.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/23.
 */
@Repository
public class MallAccessRepository {

    private JDBCAccessUtil jdbcAccessUtil;

    private Logger LOGGER = LoggerFactory.getLogger(MallAccessRepository.class);

    /**
     * 根据订单内部ID查询订单
     * @param innerOrderId
     * @return
     */
    public Integer getCountByInnerOrderId(Long companyId, Long innerOrderId) {
        return jdbcAccessUtil.queryForInt("ecommerce.SQL_SELECT_ECOMMERCE_ORDER_COUNT");
    }

    /**
     * 添加电商订单
     * @param mallOrderMessage
     * @return
     */
    public Integer addEcommerceOrder(MallOrderMessage mallOrderMessage) {
        try {
            String commodities = this.buildCommodityString(mallOrderMessage.getOrderDetail());
            jdbcAccessUtil.execute("ecommerce.SQL_INSERT_ECOMMERCE_ORDER", mallOrderMessage.getCompanyId(), mallOrderMessage.getInnerOrderId(), mallOrderMessage.getComment(), new Date(mallOrderMessage.getCreateDate()), commodities);
            return jdbcAccessUtil.queryForInt("ecommerce.SQL_FIND_LAST_ID");
        } catch (Exception e) {
            LOGGER.error("MallAccessRepository : addEcommerceOrder {} Failed {}", mallOrderMessage, e);
            return null;
        }
    }

    private String buildCommodityString(OrderDetail orderDetail) {
        if (orderDetail != null && !CollectionUtils.isEmpty(orderDetail.getCommodities())) {
            StringBuilder sb = new StringBuilder();
            List<Commodity> commodities = orderDetail.getCommodities();
            for (int i = 0; i < commodities.size(); i++) {
                Commodity commodity = commodities.get(i);
                sb.append(commodity.getName()).append(" X ").append(commodity.getCount());
                if (i != commodities.size() -1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    @Inject
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }


}
