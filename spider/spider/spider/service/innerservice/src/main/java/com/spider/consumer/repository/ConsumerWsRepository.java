package com.spider.consumer.repository;

import com.spider.common.database.JDBCAccessUtil;
import com.spider.spider.consumer.response.AddressResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@Repository
public class ConsumerWsRepository {

    private JDBCAccessUtil jdbcAccessUtil;

    public List<AddressResponse> getAddressListOfUser(Long customerId) {

        return jdbcAccessUtil.queryForList("consumer.SQL_SELECT_ADDRESS_BY_CUSTOMERID", new RowMapper<AddressResponse>() {
            @Override
            public AddressResponse mapRow(ResultSet resultSet, int i) throws SQLException {
                AddressResponse addressResponse = new AddressResponse();
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
            }
        }, customerId);

    }

    @Inject
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }


}
