package com.spider.spider.logistics;

/**
 * Created by jian.Michael on 2017/3/30.
 */
public enum  LogisticsOrderStatus {

    FOR_DELIVERY("待发货"),
    FOR_CONFIRMED("待物流公司确认"),
    INTRANSIT("运送中"),
    IN_DISTRIBUTION("配送中"),
    ALREADY_SIGN("已签收"),
    REJECTION("拒收"),
    COMPLETED("完成");

    private final String name;

    LogisticsOrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
