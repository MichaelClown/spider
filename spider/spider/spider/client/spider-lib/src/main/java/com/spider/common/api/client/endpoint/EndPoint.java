package com.spider.common.api.client.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EndPoint {

    @XmlAttribute(name = "url", required = true)
    private String url;

    @XmlAttribute(name = "context", required = true)
    private String context;

    public EndPoint() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
