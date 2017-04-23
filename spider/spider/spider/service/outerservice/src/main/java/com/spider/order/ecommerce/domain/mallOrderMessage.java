package com.spider.order.ecommerce.domain;

import java.io.Serializable;

/**
 * Created by jian.Michael on 2017/4/22.
 */
public class MallOrderMessage implements Serializable {

    private Long companyId;     //电商企业ID

    private Long createDate;    //创建时间

    private Long innerOrderId;  //内部订单号

    private String comment;     //用户留言

    private OrderDetail orderDetail;    //订单详情

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getInnerOrderId() {
        return innerOrderId;
    }

    public void setInnerOrderId(Long innerOrderId) {
        this.innerOrderId = innerOrderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}



