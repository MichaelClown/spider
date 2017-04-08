package com.spider.common.database;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jian.Michael on 2017/4/5.
 */
public class SqlRegistry {

    private final Set<String> sqlMapping = new HashSet<>();

    public SqlRegistry() {

    }

    public void registSql(String sqlMappingPath) {
        this.sqlMapping.add(sqlMappingPath);
    }

    public Set<String> getSqlMapping() {
        return sqlMapping;
    }
}
