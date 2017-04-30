package com.spider.spider.ecommerce;

/**
 * Created by jian.Michael on 2017/3/29.
 */
public enum EcommerceOrderStatus {

    NEW("已付款"),
    FOR_DELIVERY("待发货"),
    INTRANSIT("运送中"),
    CANCEL("已取消"),
    COMPLETE("已完成");

    private String name;

    EcommerceOrderStatus(String name) {
        this.name = name;
    }

}
