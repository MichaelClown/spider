package com.spider.order.logistics.domain;

import com.spider.order.Company;
import com.spider.order.ecommerce.domain.Actor;
import com.spider.order.ecommerce.domain.Address;
import com.spider.spider.logistics.LogisticsOrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jian.Michael on 2017/3/30.
 */
public class LogisticsOrder implements Serializable {

    private Long orderId;                   //订单ID

    private Actor destinationActor;         //收件人

    private Address destinationAddress;     //收件地址

    private Actor originActor;              //发件人

    private Address originAddress;          //发货地址

    private String goods;                   //发货商品

    private BigDecimal fee;                 //物流费用

    private Date createDate;                //订单创建时间

    private Date endDate;                   //订单完成时间

    private Date updateDate;                //订单更新时间

    private Company ecommerce;              //所属电商

    private Company logistics;              //所属物流

    private LogisticsOrderStatus status;    //订单状态

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Actor getDestinationActor() {
        return destinationActor;
    }

    public void setDestinationActor(Actor destinationActor) {
        this.destinationActor = destinationActor;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Actor getOriginActor() {
        return originActor;
    }

    public void setOriginActor(Actor originActor) {
        this.originActor = originActor;
    }

    public Address getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(Address originAddress) {
        this.originAddress = originAddress;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Company getEcommerce() {
        return ecommerce;
    }

    public void setEcommerce(Company ecommerce) {
        this.ecommerce = ecommerce;
    }

    public Company getLogistics() {
        return logistics;
    }

    public void setLogistics(Company logistics) {
        this.logistics = logistics;
    }

    public LogisticsOrderStatus getStatus() {
        return status;
    }

    public void setStatus(LogisticsOrderStatus status) {
        this.status = status;
    }
}
