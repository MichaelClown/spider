package com.spider.test;

import com.spider.common.database.JDBCAccessUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@Controller
public class OuterTestController {

    private JDBCAccessUtil jdbcAccessUtil;

    private JdbcTemplate jdbcTemplate;

    @Resource
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Resource
    public void setJdbcAccessUtil(JDBCAccessUtil jdbcAccessUtil) {
        this.jdbcAccessUtil = jdbcAccessUtil;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public TestResponse test(String argument) {

        System.out.println(argument);
        TestResponse testResponse = new TestResponse();
//        List<TestResponse> testResponses = jdbcAccessUtil.queryForList("actor.SQL_SELECT_HEIHEI", TestResponse.class);
//
//        String sql = "select * from actor";
//        RowMapper<TestResponse> rowMapper  = new BeanPropertyRowMapper<>(TestResponse.class);
//        TestResponse response = jdbcTemplate.queryForObject(sql, rowMapper);
        return testResponse;

    }

}
