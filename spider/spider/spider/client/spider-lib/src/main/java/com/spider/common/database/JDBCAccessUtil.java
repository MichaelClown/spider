package com.spider.common.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by jian.Michael on 2017/4/5.
 */
public class JDBCAccessUtil {

    private JdbcTemplate jdbcTemplate;

    private SqlManager sqlManager;

    private final Logger logger = LoggerFactory.getLogger(JDBCAccessUtil.class);

    public JDBCAccessUtil() {

    }

    public <T> T queryForObject(String sqlConfig, Class<T> targetClass, Object... args) {

        try {
            String sql = this.splitAndGetSql(sqlConfig);
            return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(targetClass));
        } catch (Exception e) {
            logger.error("sql with name {} got wrong result : {}", sqlConfig, e);
            return null;
        }
    }

    public <T> T queryForObject(String sqlConfig, RowMapper<T> rowMapper, Object... args) {
        try {
            String sql = this.splitAndGetSql(sqlConfig);
            return jdbcTemplate.queryForObject(sql, rowMapper, args);
        } catch (Exception e) {
            logger.error("sql with name {} got wrong result : {}", sqlConfig, e);
            return null;
        }
    }

    public <T> T queryForObjectWithSql(String sql, Class<T> targetClass, Object... args) {
        try {
            return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<T>(targetClass));
        } catch (Exception e) {
            logger.error("sql with sentence {} got wrong result : {}", sql, e);
            return null;
        }
    }

    public <T> T queryForObjectWithSql(String sql, RowMapper<T> rowMapper, Object... args) {
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, args);
        } catch (Exception e) {
            logger.error("sql with sentence {} got wrong result : {}", sql, e);
            return null;
        }
    }

    public <T> List<T> queryForList(String sqlConfig, Class<T> targetClass, Object... args) {
        try {
            String sql = this.splitAndGetSql(sqlConfig);
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(targetClass), args);
        } catch (Exception e) {
            logger.error("sql with name {} got wrong result : {}", sqlConfig, e);
            return null;
        }
    }

    public <T> List<T> queryForList(String sqlConfig, RowMapper<T> rowMapper, Object... args) {
        try {
            String sql = this.splitAndGetSql(sqlConfig);
            return jdbcTemplate.query(sql, rowMapper, args);
        } catch (Exception e) {
            logger.error("sql with name {} got wrong result : {}", sqlConfig, e);
            return null;
        }
    }

    public <T> List<T> queryForListWithSql(String sql, Class<T> targetClass, Object... args) {
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(targetClass), args);
        } catch (Exception e) {
            logger.error("sql with sentence {} got wrong result : {}", sql, e);
            return null;
        }
    }

    public <T> List<T> queryForListWithSql(String sql, RowMapper<T> rowMapper, Object... args) {
        try {
            return jdbcTemplate.query(sql, rowMapper, args);
        } catch (Exception e) {
            logger.error("sql with sentence {} got wrong result : {}", sql, e);
            return null;
        }
    }

    public Integer queryForInt(String sqlConfig, Object... args) {
        try {
            String sql = this.splitAndGetSql(sqlConfig);
            return jdbcTemplate.queryForObject(sql, Integer.class, args);
        } catch (Exception e) {
            logger.error("sql with name {} got wrong result : {}", sqlConfig, e);
            return null;
        }
    }

    public Integer queryForIntWithSql(String sql, Object... args) {
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, args);
        } catch (Exception e) {
            logger.error("sql with sentence {} got wrong result : {}", sql, e);
            return null;
        }
    }

    public int execute(String sqlConfig, Object... args) {
        try {
            String sql = this.splitAndGetSql(sqlConfig);
            return jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            logger.error("sql with name {} got wrong result : {}", sqlConfig, e);
            return 0;
        }
    }

    public int executeWithSql(String sql, Object... args) {
        try {
            return jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            logger.error("sql with sentence {} got wrong result : {}", sql, e);
            return 0;
        }
    }

    public String queryForString(String sqlConfig, Object... args) {
        try {
            String sql = this.splitAndGetSql(sqlConfig);
            return jdbcTemplate.queryForObject(sql, args, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString(1);
                }
            });
        } catch (Exception e) {
            logger.error("sql with name {} got wrong result : {}", sqlConfig, e);
            return null;
        }
    }

    public String queryForStringWithSql(String sql, Object... args) {
        try {
            return jdbcTemplate.queryForObject(sql, args, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString(1);
                }
            });
        } catch (Exception e) {
            logger.error("sql with sentence {} got wrong result : {}", sql, e);
            return null;
        }
    }

    public String splitAndGetSql(String sqlConfig) throws Exception {
        String[] sqlConfigs = sqlConfig.split("\\.");
        if (sqlConfigs.length != 2) {
            throw new Exception("sql别名错误 : " + sqlConfig);
        }

        String tableName = sqlConfigs[0];
        String sqlName = sqlConfigs[1];
        Map<String, String> sqlMap = this.sqlManager.getValues().get(tableName);
        if (CollectionUtils.isEmpty(sqlMap)) {
            throw new Exception("sql别名 " + sqlConfig + "表名不存在 : " + tableName);
        }

        String sql = sqlMap.get(sqlName);
        if (StringUtils.hasText(sql)) {
            return sql;
        } else {
            throw new Exception("sql别名 " + sqlConfig +  "sql名不存在 : " + sqlName);
        }
    }

    public void setSqlManager(SqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
