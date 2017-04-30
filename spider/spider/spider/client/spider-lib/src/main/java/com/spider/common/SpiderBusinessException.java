package com.spider.common;

/**
 * Created by jian.Michael on 2017/3/29.
 */
public class SpiderBusinessException extends RuntimeException {

    private String code;

    public SpiderBusinessException(String message) {
        super(message);
    }

    public SpiderBusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public SpiderBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
