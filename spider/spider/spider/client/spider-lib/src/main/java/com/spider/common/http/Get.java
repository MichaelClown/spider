package com.spider.common.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

/**
 * Created by jian.Michael on 2017/2/8.
 */
public class Get extends SpiderHttpRequest {

    public Get(String url) {
        super(url);
    }

    @Override
    HttpRequestBase toHttpRequest() throws IOException {
        return new HttpGet(this.url);
    }
}
