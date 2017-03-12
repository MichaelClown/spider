package com.spider.common.http;

/**
 * Created by jian.Michael on 2017/3/12.
 */
public class SpiderHttpBinaryResponse {

    private final SpiderHttpStatusCode statusCode;

    private final SpiderHttpHeaders headers;

    private final byte[] responseBytes;

    public SpiderHttpBinaryResponse(SpiderHttpStatusCode statusCode, SpiderHttpHeaders headers, byte[] responseBytes) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.responseBytes = responseBytes;
    }

    public SpiderHttpStatusCode getStatusCode() {
        return statusCode;
    }

    public SpiderHttpHeaders getHeaders() {
        return headers;
    }

    public byte[] getResponseBytes() {
        return responseBytes;
    }
}
