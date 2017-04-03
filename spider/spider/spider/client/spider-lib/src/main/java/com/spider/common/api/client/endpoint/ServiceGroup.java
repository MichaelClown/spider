package com.spider.common.api.client.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceGroup {

    @XmlAttribute(name = "name", required = true)
    private String groupName;

    @XmlElement(name = "service", required = true)
    private List<Service> services;

    public ServiceGroup() {

    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
