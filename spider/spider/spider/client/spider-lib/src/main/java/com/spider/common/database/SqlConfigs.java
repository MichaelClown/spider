package com.spider.common.database;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/5.
 */

@XmlRootElement(namespace = "sql-config")
@XmlAccessorType(XmlAccessType.FIELD)
public class SqlConfigs implements Serializable {

    @XmlAttribute(name = "namespace", required = true)
    private String namespace;

    @XmlElement(name = "sql")
    private List<SqlConfig> sqlConfigs = new ArrayList<>();

    public SqlConfigs() {
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<SqlConfig> getSqlConfigs() {
        return sqlConfigs;
    }

    public void setSqlConfigs(List<SqlConfig> sqlConfigs) {
        this.sqlConfigs = sqlConfigs;
    }
}

