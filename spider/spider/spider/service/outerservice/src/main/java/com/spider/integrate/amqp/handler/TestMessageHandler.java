package com.spider.integrate.amqp.handler;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by jian.Michael on 2017/1/26.
 */
public class TestMessageHandler implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println(message.toString());

    }

}
