package com.spider.order.logistics.domain;

import com.spider.spider.logistics.LogisticsOrderStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 物流单相关消息
 * Created by jian.Michael on 2017/4/20.
 */
public class LogisticsOrderMessage {

    public enum MsgType {
        NEW,UPDATE,DELETE
    }

    private Long orderId;

    private MsgType msgType;

    private String from;

    private String to;

    private String detail;

    private String status;

    private Date updateDate;    //物流单创建时间

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
