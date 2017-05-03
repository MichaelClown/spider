package com.spider.logistics.web;

import com.spider.logistics.sevice.LogisticsWsService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Created by drcdnb20 on 17-4-3.
 */
@Controller
@RequestMapping("/logistics")
public class LogisticsWsController {

    private LogisticsWsService logisticsWsService;

    @RequestMapping(value = "/sign/{logisticsOrderId}/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean sign(@PathVariable String logisticsOrderId, @PathVariable String customerId) {
        return logisticsWsService.sign(Long.parseLong(logisticsOrderId), Long.parseLong(customerId));
    }

    @Inject
    public void setLogisticsWsService(LogisticsWsService logisticsWsService) {
        this.logisticsWsService = logisticsWsService;
    }
}
