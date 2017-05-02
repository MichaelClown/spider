package com.spider.consumer.web;

import com.spider.consumer.service.ConsumerWsService;
import com.spider.spider.consumer.response.AddressResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@Controller
public class ConsumerWsController {

    private ConsumerWsService consumerWsService;

    @RequestMapping(value = "/addresslist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressResponse> getAddressListOfUser(String customerId) {
        return consumerWsService.getAddressListOfUser(Long.parseLong(customerId));
    }

    @Inject
    public void setConsumerWsService(ConsumerWsService consumerWsService) {
        this.consumerWsService = consumerWsService;
    }
}
