package com.spider.common.http;

/**
 * Created by jian.Michael on 2017/2/2.
 */
public class SpiderHttpResponse {

    private final SpiderHttpStatusCode statusCode;

    private final SpiderHttpHeaders headers;

    private final String responseText;

    public SpiderHttpResponse(SpiderHttpStatusCode statusCode, SpiderHttpHeaders headers, String responseText) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.responseText = responseText;
    }

    public SpiderHttpStatusCode getStatusCode() {
        return statusCode;
    }

    public SpiderHttpHeaders getHeaders() {
        return headers;
    }

    public String getResponseText() {
        return responseText;
    }
}
