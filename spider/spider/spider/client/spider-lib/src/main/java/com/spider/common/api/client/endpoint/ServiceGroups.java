package com.spider.common.api.client.endpoint;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@XmlRootElement(name = "endpoint-groups")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceGroups {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "group")
    private List<ServiceGroup> serviceGroups;

    public ServiceGroups() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceGroup> getServiceGroups() {
        return serviceGroups;
    }

    public void setServiceGroups(List<ServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
    }
}
