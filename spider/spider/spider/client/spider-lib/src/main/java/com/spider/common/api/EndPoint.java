package com.spider.common.api;

/**
 * Created by jian.Michael on 2017/4/3.
 */
public enum EndPoint {

    INNER("INNER_SERVICE"),
    OUTER("OUTER_SERVICE");

    EndPoint(String name) {
        this.name = name;
    }

    private final String name;

    public static String getName(Enum endpoint) {
        for (EndPoint ep : EndPoint.values()) {
            if (ep.name().equals(endpoint.name())) {
                return ep.name;
            }
        }
        return null;
    }
}
