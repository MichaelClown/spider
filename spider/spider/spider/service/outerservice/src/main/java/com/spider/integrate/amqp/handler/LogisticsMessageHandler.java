package com.spider.integrate.amqp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;

/**
 * 物流订单的状态变更消息
 * Created by jian.Michael on 2017/3/20.
 */
public class LogisticsMessageHandler implements MessageListener{

    private Logger LOGGER = LoggerFactory.getLogger(LogisticsMessageHandler.class);

    private static final String KEY_ACTION = "action";

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
    }

    private void handleMessage() {

    }

    /**
     * 各个消息的routingkey定义
     *
     * @author jian.ma
     */
    public static class RoutingKey {
        public static final String SPIDER_SYNC_ORDER = "spider.sync.order";

    }

}
