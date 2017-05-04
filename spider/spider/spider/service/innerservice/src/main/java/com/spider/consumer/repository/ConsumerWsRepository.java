package com.spider.consumer.repository;

import com.spider.common.SpiderBusinessException;
import com.spider.common.database.JDBCAccessUtil;
import com.spider.spider.consumer.response.AddressResponse;
import com.spider.spider.order.Actor;
import com.spider.spider.order.Address;
import com.spider.spider.order.OrderRecordItem;
import com.spider.spider.order.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@Repository
public class ConsumerWsRepository {

    private Logger logger = LoggerFactory.getLogger(ConsumerWsRepository.class);

    private JDBCAccessUtil jdbcAccessUtil;

    public List<AddressResponse> getAddressListOfUser(Long customerId) {
        return jdbcAccessUtil.queryForList("consumer.SQL_SELECT_ADDRESS_BY_CUSTOMERID", ADDRESSMAPPER, customerId);
    }

    public AddressResponse getAddress(Long customerId, Long addressId) {
        return jdbcAccessUtil.queryForObject("consumer.SQL_SELECT_ADDRESS_BY_ADDRESSID", ADDRESSMAPPER, customerId, addressId);
    }

    public Boolean deleteAddress(Long customerId, Long addressId) {
        Integer result = jdbcAccessUtil.execute("consumer.SQL_DELETE_ADDRESS_BY_ADDRESSID", customerId, addressId);
        return result > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean updateAddress(AddressResponse addressResponse) {
        List<Object> params = new ArrayList();
        String finalSql = buildSqlUpdateAddress(addressResponse, params);
        Integer result = jdbcAccessUtil.executeWithSql(finalSql, params.toArray());
        return result > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private String buildSqlUpdateAddress(AddressResponse addressResponse, List<Object> params) {
        StringBuilder sb = new StringBuilder("update address t1 left join actor t2 on t1.actor_id = t2.actor_id set ");
        if (StringUtils.hasText(addressResponse.getCellPhone())) {
            sb.append("t2.cell_phone = %s,");
            params.add(addressResponse.getCellPhone());
        }
        if (StringUtils.hasText(addressResponse.getUserName())) {
            sb.append("t2.real_name = %s,");
            params.add(addressResponse.getUserName());
        }
        if (StringUtils.hasText(addressResponse.getProvince())) {
            sb.append("t1.province = %s,");
            params.add(addressResponse.getProvince());
        }
        if (StringUtils.hasText(addressResponse.getCity())) {
            sb.append("t1.city = %s,");
            params.add(addressResponse.getCity());
        }
        if (StringUtils.hasText(addressResponse.getDistrict())) {
            sb.append("t1.district = %s,");
            params.add(addressResponse.getDistrict());
        }
        if (StringUtils.hasText(addressResponse.getCounty())) {
            sb.append("t1.county = %s,");
            params.add(addressResponse.getCounty());
        }
        if (StringUtils.hasText(addressResponse.getTown())) {
            sb.append("t1.town = %s,");
            params.add(addressResponse.getTown());
        }
        if (StringUtils.hasText(addressResponse.getCompanyName())) {
            sb.append("t1.company = %s,");
            params.add(addressResponse.getCompanyName());
        }
        if (StringUtils.hasText(addressResponse.getDetail())) {
            sb.append("t1.detail = %s,");
            params.add(addressResponse.getDetail());
        }
        if (addressResponse.getZipCode() != null) {
            sb.append("t1.zip_code = %s ");
            params.add(addressResponse.getZipCode());
        }
        sb.append("where t1.address_id = %s");
        params.add(addressResponse.getAddressId());
        return sb.toString();
    }

    public AddressResponse insertAddress(AddressResponse addressResponse) {
        jdbcAccessUtil.execute("account.SQL_INSERT_ACTOR", addressResponse.getCellPhone(), addressResponse.getUserName(), addressResponse.getCustomerId());
        Integer actorId = jdbcAccessUtil.queryForInt("account.SQL_FIND_LAST_ID");
        if (actorId == null) {
            logger.error("Failed to add actor with cellPhone {} and userName {} of {}", addressResponse.getCellPhone(), addressResponse.getUserName(), addressResponse.getCustomerId());
            throw new SpiderBusinessException(String.format("Failed to add actor with cellPhone %s and userName %s of %s", addressResponse.getCellPhone(), addressResponse.getUserName(), addressResponse.getCustomerId()));
        }
        jdbcAccessUtil.execute("consumer.SQL_INSERT_ADDRESS", addressResponse.getProvince(), addressResponse.getCity(), addressResponse.getDistrict(), addressResponse.getCounty(), addressResponse.getTown(), addressResponse.getZipCode(), actorId, addressResponse.getCompanyName(), new Date());
        Integer addressId = jdbcAccessUtil.queryForInt("consumer.SQL_FIND_LAST_ID");
        if (addressId == null) {
            logger.error("Failed to add address with actorId {} of {}", actorId, addressResponse.getCustomerId());
            throw new SpiderBusinessException(String.format("Failed to add address with actorId %s of %s", actorId, addressResponse.getCustomerId()));

        }
        AddressResponse response = getAddress(addressResponse.getCustomerId(), addressId.longValue());
        return response;
    }

    private RowMapper<AddressResponse> ADDRESSMAPPER = (resultSet, i) -> {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setActorId(resultSet.getLong("actor_id"));
        addressResponse.setCellPhone(resultSet.getString("cell_phone"));
        addressResponse.setUserName(resultSet.getString("real_name"));
        addressResponse.setProvince(resultSet.getString("province"));
        addressResponse.setCity(resultSet.getString("city"));
        addressResponse.setDistrict(resultSet.getString("district"));
        addressResponse.setCounty(resultSet.getString("county"));
        addressResponse.setTown(resultSet.getString("town"));
        addressResponse.setDetail(resultSet.getString("detail"));
        addressResponse.setCompanyName(resultSet.getString("company"));
        addressResponse.setZipCode(resultSet.getInt("zip_code"));
        addressResponse.setAddressId(resultSet.getLong("address_id"));
        addressResponse.setCreateDate(resultSet.getDate("create_date"));
        return addressResponse;
    };

    public List<OrderResponse> getOrderListOfUser(Long customerId) {
        return jdbcAccessUtil.queryForList("consumer.SQL_SELECT_ORDER_BY_CUSTOMERID", ORDERMAPPER,customerId);
    }

    private RowMapper<OrderResponse> ORDERMAPPER = (resultSet, i) -> {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setLogisticsOrderStatus(resultSet.getString("status"));
        orderResponse.setCommodities(resultSet.getString("commodities"));
        orderResponse.setEcommerceOrderId(resultSet.getLong("id"));
        orderResponse.setCreateDate(resultSet.getDate("create_date"));
        return orderResponse;
    };

    public OrderResponse getOrderDetailByOrderId(Long orderId) {
        return jdbcAccessUtil.queryForObject("logistics.SQL_SELECT_ORDER_DETAIL", ORDERDETAILMAPPER, orderId);
    }

    private RowMapper<OrderResponse> ORDERDETAILMAPPER = (resultSet, i) -> {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setLogisticsOrderStatus(resultSet.getString("l_status"));
        orderResponse.setCommerceOrderStatus(resultSet.getString("e_status"));
        orderResponse.setCreateDate(resultSet.getDate("l_create_date"));
        orderResponse.setComment(resultSet.getString("comment"));
        orderResponse.setCommodities(resultSet.getString("commodities"));
        orderResponse.setFee(resultSet.getBigDecimal("l_fee"));
        Actor dActor = new Actor();
        dActor.setRealName("d_actor_name");
        dActor.setCellPhone("d_actor_cellphone");
        orderResponse.setDestinationActor(dActor);
        Actor oActor = new Actor();
        oActor.setRealName("o_actor_name");
        oActor.setCellPhone("o_actor_cellphone");
        orderResponse.setOriginActor(oActor);
        Address dAddress = new Address();
        dAddress.setProvince(resultSet.getString("d_province"));
        dAddress.setCity(resultSet.getString("d_city"));
        dAddress.setArea(resultSet.getString("d_district"));
        dAddress.setZipCode(resultSet.getInt("d_zip_code"));
        dAddress.setDetail(resultSet.getString("d_detail"));
        dAddress.setCompanyName(resultSet.getString("d_company"));
        orderResponse.setDestinationAddress(dAddress);
        Address oAddress = new Address();
        oAddress.setProvince(resultSet.getString("o_province"));
        oAddress.setCity(resultSet.getString("o_city"));
        oAddress.setArea(resultSet.getString("o_district"));
        oAddress.setZipCode(resultSet.getInt("o_zip_code"));
        oAddress.setDetail(resultSet.getString("o_detail"));
        oAddress.setCompanyName(resultSet.getString("o_company"));
        orderResponse.setDestinationAddress(oAddress);
        orderResponse.setEcommerceCompany(resultSet.getString("e_company"));
        orderResponse.setLogisticsCompany("l_company");
        return orderResponse;
    };

    public List<OrderRecordItem> getOrderRecordList(Long orderId) {
        return jdbcAccessUtil.queryForList("logistics.SQL_SELECT_RECORD_OF_ORDER", ORDERRECORDMAPPER, orderId);
    }

    private RowMapper<OrderRecordItem> ORDERRECORDMAPPER = (resultSet, i) -> {
        OrderRecordItem orderRecordItem = new OrderRecordItem();
        orderRecordItem.setMessage(resultSet.getString("order_id"));
        orderRecordItem.setRecordDate(resultSet.getDate("update_date"));
        return orderRecordItem;
    };

    @Inject
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }


}
