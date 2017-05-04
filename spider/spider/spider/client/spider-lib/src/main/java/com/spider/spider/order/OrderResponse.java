package com.spider.spider.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
public class OrderResponse implements Serializable {

    private Long customerId;

    private Long ecommerceOrderId;

    private String ecommerceCompany;

    private String comment;

    private Date createDate;

    private String commodities;

    private String commerceOrderStatus;

    private Long logisticsOrderId;

    private BigDecimal fee;

    private String logisticsOrderStatus;

    private String logisticsCompany;

    private Actor destinationActor;

    private Address destinationAddress;

    private Actor originActor;

    private Address originAddress;

    private List<OrderRecordItem> orderRecords;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEcommerceOrderId() {
        return ecommerceOrderId;
    }

    public void setEcommerceOrderId(Long ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

    public String getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(String ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCommodities() {
        return commodities;
    }

    public void setCommodities(String commodities) {
        this.commodities = commodities;
    }

    public String getCommerceOrderStatus() {
        return commerceOrderStatus;
    }

    public void setCommerceOrderStatus(String commerceOrderStatus) {
        this.commerceOrderStatus = commerceOrderStatus;
    }

    public Long getLogisticsOrderId() {
        return logisticsOrderId;
    }

    public void setLogisticsOrderId(Long logisticsOrderId) {
        this.logisticsOrderId = logisticsOrderId;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getLogisticsOrderStatus() {
        return logisticsOrderStatus;
    }

    public void setLogisticsOrderStatus(String logisticsOrderStatus) {
        this.logisticsOrderStatus = logisticsOrderStatus;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
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

    public List<OrderRecordItem> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(List<OrderRecordItem> orderRecords) {
        this.orderRecords = orderRecords;
    }
}
