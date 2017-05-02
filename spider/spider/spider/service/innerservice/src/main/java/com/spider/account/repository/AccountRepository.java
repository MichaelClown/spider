package com.spider.account.repository;

import com.spider.common.database.JDBCAccessUtil;
import com.spider.spider.account.response.UserAccountResponse;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/4/2.
 */
@Repository
public class AccountRepository {

    private JDBCAccessUtil jdbcAccessUtil;

    public UserAccountResponse login(String cellPhone) {
        UserAccountResponse userAccountResponse =  jdbcAccessUtil.queryForObject("account.SQL_SELECT_ACCOUNT_BY_CELLPHONE", (resultSet, i) -> {
            UserAccountResponse response = new UserAccountResponse();
            response.setCustomerId(resultSet.getLong("customer_id"));
            response.setCellPhone(resultSet.getString("cell_phone"));
            response.setType(resultSet.getString("type"));
            response.setUserName(resultSet.getString("user_name"));
            response.setCreateDate(resultSet.getDate("create_date"));
            return response;
        }, cellPhone);
        return userAccountResponse == null ? new UserAccountResponse() : userAccountResponse;
    }

    @Inject
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }
}
