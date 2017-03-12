package com.spider.common.http;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by jian.Michael on 2017/2/2.
 */
public abstract class SpiderHttpRequest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final String url;

    private final SpiderHttpHeaders headers = new SpiderHttpHeaders();

    public SpiderHttpRequest(String url) {
        this.url = url;
    }

    public void addHeader(String name, String value) {
        this.headers.add(name, value);
    }

    public void setAccept(String contentType) {
        this.headers.add("Accept", contentType);
    }

    abstract HttpRequestBase toHttpRequest() throws IOException;

    public HttpRequestBase createHttpRequest() throws IOException {
        HttpRequestBase request = this.toHttpRequest();
        this.headers.addHeadersToHttpRequest(request);
        return request;
    }
}

