package com.spider;


import com.spider.common.database.JDBCAccessUtil;
import com.spider.common.database.SqlManager;
import com.spider.common.database.SqlRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;

/**
 * Created by jian.Michael on 2017/4/5.
 */
public abstract class DbConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JndiObjectFactoryBean factoryBean() {
        JndiObjectFactoryBean objectFactoryBean = new JndiObjectFactoryBean();
        objectFactoryBean.setJndiName("java:comp/env/jdbc/spiderds");
        return objectFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        return (DataSource) factoryBean().getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public SqlManager sqlManager() {
        SqlManager sqlManager = SqlManager.getInstance();
        SqlRegistry sqlRegistry = sqlManager.getSqlRegistry();
        sqlRegistry.registSql("config/db/sql/actor-sqlmap.xml");
        sqlManager.initialize();
        return sqlManager;
    }

    @Bean
    public JDBCAccessUtil jdbcAccessUtil() {
        JDBCAccessUtil jdbcAccessUtil = new JDBCAccessUtil();
        jdbcAccessUtil.setJdbcTemplate(jdbcTemplate());
        jdbcAccessUtil.setSqlManager(sqlManager());
        return jdbcAccessUtil;
    }
}
