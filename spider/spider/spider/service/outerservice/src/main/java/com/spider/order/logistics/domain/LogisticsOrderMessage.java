package com.spider.order.logistics.domain;

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


    private MsgType msgType;

    private Long orderId;   //订单ID

    private Long destinationActorId;    //收件人ID

    private Long destinationAddressId;  //收件地址ID

    private Long originActorId;     //发货人ID

    private Long originAddressId;   //发货人地址ID

    private BigDecimal fee;     //物流费用

    private Date createDate;    //物流单创建时间

    private Date endDate;       //物流单完成时间

    private Long eCommerceId;     //订单所属电商公司ID

    private Long logisticsId;     //订单所属物流公司ID



}
