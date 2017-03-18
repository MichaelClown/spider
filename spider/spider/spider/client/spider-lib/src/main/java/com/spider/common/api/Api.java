package com.spider.common.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jian.Michael on 2017/3/8.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Api {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "endpoint", required = true)
    private String endpoint;


    public Api() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
