package com.spider.integrate.amqp.handler;

import com.alibaba.fastjson.JSON;
import com.spider.order.ecommerce.domain.MallOrderMessage;
import com.spider.order.ecommerce.service.MallAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;

import javax.inject.Inject;

/**
 * 电商的订单消息
 * Created by jian.Michael on 2017/3/20.
 */
public class EcommerceMessageHandler implements MessageListener{

    private Logger LOGGER = LoggerFactory.getLogger(EcommerceMessageHandler.class);

    private static final String KEY_ACTION = "action";

    private MallAccessService mallAccessService;

    @Override
    public void onMessage(Message message) {
        MessageProperties msgProps = message.getMessageProperties();
        Object routekey = msgProps.getHeaders().get(RepublishMessageRecoverer.X_ORIGINAL_ROUTING_KEY);
        if (routekey == null) {
            routekey = message.getMessageProperties().getReceivedRoutingKey();
        }
        LOGGER.info("EcommerceOrderMessageHandler: action: " + (String) msgProps.getHeaders().get(KEY_ACTION)
                + ", routeKey: " + routekey
                + ", message body: " + new String(message.getBody()));
        MallOrderMessage mallOrderMessage = JSON.parseObject(new String(message.getBody()), MallOrderMessage.class);
        if (mallOrderMessage != null && mallOrderMessage.getInnerOrderId() != null && mallOrderMessage.getCompanyId() != null) {
            handleMessage(mallOrderMessage);
        } else {
            LOGGER.warn("EcommerceOrderMessageHandler : ignore message {} because of incomplete filed", mallOrderMessage.toString());
        }
    }

    private void handleMessage(MallOrderMessage mallOrderMessage) {
        try {
            mallAccessService.processMessage(mallOrderMessage);
            LOGGER.info("EcommerceMessageHandler : MallOrderMessage processed. " +mallOrderMessage.toString());
        } catch (Exception e) {
            LOGGER.error("Exception in handler message", e);
            throw e;
        }
    }

    @Inject
    public void setMallAccessService(MallAccessService mallAccessService) {
        this.mallAccessService = mallAccessService;
    }
}
