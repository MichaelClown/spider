package com.spider.integrate.amqp.handler;

import com.alibaba.fastjson.JSON;
import com.spider.order.logistics.domain.LogisticsOrderMessage;
import com.spider.order.logistics.service.LogisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;

import javax.inject.Inject;

/**
 * 物流订单的状态变更消息
 * Created by jian.Michael on 2017/3/20.
 */
public class LogisticsMessageHandler implements MessageListener{

    private Logger LOGGER = LoggerFactory.getLogger(LogisticsMessageHandler.class);

    private static final String KEY_ACTION = "action";

    private LogisticsService logisticsService;

    @Override
    public void onMessage(Message message) {
        MessageProperties msgProps = message.getMessageProperties();
        Object routekey = msgProps.getHeaders().get(RepublishMessageRecoverer.X_ORIGINAL_ROUTING_KEY);
        if (routekey == null) {
            routekey = message.getMessageProperties().getReceivedRoutingKey();
        }
        LOGGER.info("ActorHandler: action: " + (String) msgProps.getHeaders().get(KEY_ACTION)
                + ", routeKey: " + routekey
                + ", message body: " + new String(message.getBody()));
        LogisticsOrderMessage logisticsOrderMessage = JSON.parseObject(new String(message.getBody()), LogisticsOrderMessage.class);
        if (logisticsOrderMessage != null && logisticsOrderMessage.getOrderId() != null) {
            handleMessage(logisticsOrderMessage);
        } else {
            LOGGER.warn("LogisticsMessageHandler : ignore message {} because of incomplete filed", logisticsOrderMessage.toString());
        }
    }

    private void handleMessage(LogisticsOrderMessage orderMessage) {
        try {
            logisticsService.processMessage(orderMessage);
            LOGGER.info("LogisticsMessageHandler : LogisticsOrderMessage processed. " +orderMessage.toString());
        } catch (Exception e) {
            LOGGER.error("Exception in handler message", e);
            throw e;
        }
    }

    /**
     * 各个消息的routingkey定义
     *
     * @author jian.ma
     */
    public static class RoutingKey {
        public static final String SPIDER_SYNC_ORDER = "spider.sync.order";

    }

    @Inject
    public void setLogisticsService(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }
}
