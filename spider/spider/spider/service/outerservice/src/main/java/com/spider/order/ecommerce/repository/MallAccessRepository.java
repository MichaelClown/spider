package com.spider.order.ecommerce.repository;

import com.spider.common.database.JDBCAccessUtil;
import com.spider.order.ecommerce.domain.*;
import com.spider.order.logistics.domain.LogisticsOrder;
import com.spider.spider.ecommerce.EcommerceOrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            jdbcAccessUtil.execute("ecommerce.SQL_INSERT_ECOMMERCE_ORDER", mallOrderMessage.getCompanyId(), mallOrderMessage.getInnerOrderId(), mallOrderMessage.getComment(), new Date(mallOrderMessage.getCreateDate()), commodities, EcommerceOrderStatus.NEW);
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

    //查询用户账户信息
    public Customer lockOnCustomer(String cellPhone) {
        return jdbcAccessUtil.queryForObject("actor.SQL_SELECT_CUSTOMER_BY_CELLPHONE_FOR_UPDATE", CUSTOMERMAPPER, cellPhone);
    }

    private RowMapper<Customer> CUSTOMERMAPPER = (resultSet, i) -> {
        Customer customer = new Customer();
        customer.setCellPhone(resultSet.getString("cell_phone"));
        customer.setEmail(resultSet.getString("email"));
        customer.setCustomerId(resultSet.getLong("customer_id"));
        customer.setUserName(resultSet.getString("user_name"));
        return customer;
    };

    //添加用户信息
    public Integer addCustomer(Customer customer) {
        try {
            jdbcAccessUtil.execute("actor.SQL_INSERT_CUSTOMER", customer.getUserName(), customer.getCellPhone(), customer.getEmail(), new Date());
            Integer customerId = jdbcAccessUtil.queryForInt("actor.SQL_FIND_LAST_ID");
            return customerId;
        } catch (RuntimeException re) {
            LOGGER.warn("Failed to add customer {} with exception {}", customer, re);
            return null;
        }
    }

    @Inject
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }

    public Actor lockOnActor(String cellPhone, Long customerId) {
        return jdbcAccessUtil.queryForObject("actor.SQL_SELECT_ACTOR_BY_CELLPHONE_FOR_UPDATE", ACTORMAPPER, cellPhone, customerId);
    }

    // 添加收件人信息
    public Integer addActor(Actor actor) {
        try {
            jdbcAccessUtil.execute("actor.SQL_INSERT_ACTOR", actor.getCustomerId(), actor.getRealName(), actor.getSex(), actor.getCustomerId(), new Date());
            Integer actorId = jdbcAccessUtil.queryForInt("actor.SQL_FIND_LAST_ID");
            return actorId;
        } catch (RuntimeException re) {
            LOGGER.warn("Failed to add actor {} with exception {}", actor, re);
            return null;
        }
    }

    private RowMapper<Actor> ACTORMAPPER = (resultSet, i) -> {
        Actor actor = new Actor();
        actor.setCustomerId(resultSet.getLong("customer_id"));
        actor.setActorId(resultSet.getInt("actor_id"));
        actor.setRealName(resultSet.getString("real_name"));
        actor.setCellPhone(resultSet.getString("cell_phone"));
        actor.setUpdateDate(resultSet.getLong("update_date"));
        actor.setCreateDate(resultSet.getDate("create_date"));
        return actor;
    };

    // 查询地址信息并加行级锁
    public Address lockOnAddress(Long outId) {
        return jdbcAccessUtil.queryForObject("address.SQL_SELECT_ADDRESS_BY_ID_FOR_UPDATE", ADDRESSMAPPER, outId);
    }

    private RowMapper<Address> ADDRESSMAPPER = new RowMapper<Address>() {
        @Override
        public Address mapRow(ResultSet resultSet, int i) throws SQLException {
            Address address = new Address();
            address.setAddressId(resultSet.getLong("outer_id"));
            address.setProvince(resultSet.getString("province"));
            address.setCity(resultSet.getString("city"));
            address.setArea(resultSet.getString("distinct"));
            address.setCompanyName(resultSet.getString("company"));
            address.setDetail(resultSet.getString("detail"));
            address.setZipCode(resultSet.getInt("zip_code"));
            return address;
        }
    };

    // 添加地址信息
    public Integer addAddress(Address address) {
        try {
            jdbcAccessUtil.execute("address.SQL_FIND_LAST_ID", address.getProvince(), address.getCity(), address.getArea(), null, null, address.getDetail(), address.getZipCode(), address.getActorId(), address.getCompanyName(), new Date(), address.getAddressId());
            Integer addressId = jdbcAccessUtil.queryForInt("address.SQL_FIND_LAST_ID");
            return addressId;
        } catch (RuntimeException re) {
            LOGGER.warn("Failed to add address {} with exception {}", address, re);
            return null;
        }
    }

    // 新建物流订单
    public Integer addLogisticsOrder(LogisticsOrder logisticsOrder) {
        try {
            jdbcAccessUtil.execute("logistics.SQL_INSERT_LOGISTICS_ORDER", logisticsOrder.getDestinationActor().getActorId(), logisticsOrder.getDestinationAddress().getAddressId(), new Date(), logisticsOrder.getEcommerce().getCompanyId(), logisticsOrder.getStatus().name());
            Integer orderId = jdbcAccessUtil.queryForInt("logistics.SQL_FIND_LAST_ID");
            return orderId;
        } catch (RuntimeException re) {
            LOGGER.warn("Failed to add logistics_order {} with exception {}", logisticsOrder, re);
            return null;
        }
    }

}
