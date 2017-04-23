package com.spider.order.ecommerce.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/23.
 */
public class OrderDetail implements Serializable {

    private Customer customer;

    private Address address;

    private Customer seller;

    private List<Commodity> commodities;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<Commodity> commodities) {
        this.commodities = commodities;
    }
}