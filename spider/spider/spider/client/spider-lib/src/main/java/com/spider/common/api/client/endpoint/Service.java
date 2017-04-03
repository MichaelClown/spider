package com.spider.common.api.client.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {

    @XmlAttribute(name = "name", required = true)
    private String serviceName;

    @XmlElement(name = "endpoint", required = true)
    private EndPoint endPoint;

    public Service() {

    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }
}
