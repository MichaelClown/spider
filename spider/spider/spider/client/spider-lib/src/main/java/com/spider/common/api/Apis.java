package com.spider.common.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jian.Michael on 2017/3/6.
 */
@XmlRootElement(name = "apis")
@XmlAccessorType(XmlAccessType.FIELD)
public class Apis {

    @XmlElement(name = "api")
    private List<Api> apis = new ArrayList<>();

    private final Map<String, Api> apiMap = new ConcurrentHashMap<>();

    public Apis() {

    }

    public void init() {
        Iterator iterator = apis.iterator();
        while (iterator.hasNext()) {
            Api api = (Api) iterator.next();
            this.apiMap.put(api.getName(), api);
        }
    }

    public List<Api> getApis() {
        return this.apis;
    }

    public void setApis(List<Api> apis) {
        this.apis = apis;
    }

    public Api getApi(String name) {
        return this.apiMap.get(name);
    }
}
