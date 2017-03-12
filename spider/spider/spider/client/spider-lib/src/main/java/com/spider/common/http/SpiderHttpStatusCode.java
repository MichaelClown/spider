package com.spider.common.http;

/**
 * Created by jian.Michael on 2017/3/12.
 */
public class SpiderHttpStatusCode {

    private final int statusCode;

    public SpiderHttpStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isRedirect() {
        return this.statusCode == 302 || this.statusCode == 301 || this.statusCode == 303 || this.statusCode == 307;
    }

    public boolean isSuccess() {
        return this.statusCode >= 200 && this.statusCode <= 207;
    }

    public boolean isServerError() {
        return this.statusCode >= 500 && this.statusCode <= 507;
    }

}
