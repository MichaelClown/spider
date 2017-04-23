package com.spider.order.ecommerce.domain;

import java.io.Serializable;

/**
 * 商品
 */
public class Commodity implements Serializable {

    private String name;

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
