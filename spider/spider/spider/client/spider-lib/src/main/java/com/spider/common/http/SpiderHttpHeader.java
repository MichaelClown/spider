package com.spider.common.http;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * Created by jian.Michael on 2017/3/12.
 */
public class SpiderHttpHeader {

    private final String name;

    private final String value;

    public SpiderHttpHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Header toHttpHeader() {
        return new BasicHeader(this.name, this.value);
    }
}
