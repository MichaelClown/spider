package com.spider.common.database;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * Created by jian.Michael on 2017/4/5.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SqlConfig implements Serializable {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "value", required = true)
    private String value;

    public SqlConfig() {
    }

    public SqlConfig(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
