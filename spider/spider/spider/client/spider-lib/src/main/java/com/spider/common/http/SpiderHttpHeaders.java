package com.spider.common.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jian.Michael on 2017/3/12.
 */
public class SpiderHttpHeaders {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected List<SpiderHttpHeader> headers;

    public SpiderHttpHeaders() {

    }

    public static SpiderHttpHeaders createResponseHeaders(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        List<SpiderHttpHeader> spiderHeaders = new ArrayList<>(headers.length);
        for (Header header : headers) {
            spiderHeaders.add(new SpiderHttpHeader(header.getName(), header.getValue()));
        }

        SpiderHttpHeaders var1 = new SpiderHttpHeaders();
        var1.headers = Collections.unmodifiableList(spiderHeaders);
        return var1;
    }

    void addHeadersToHttpRequest(HttpRequestBase request) {
        if (this.headers != null) {
            for (SpiderHttpHeader header : headers) {
                request.addHeader(header.toHttpHeader());
            }
        }
    }

    public void add(String name, String value) {
        if (this.headers == null) {
            this.headers = new ArrayList<>();
        }
        this.headers.add(new SpiderHttpHeader(name, value));
    }
}
