package com.spider.consumer.web;

import com.spider.consumer.service.ConsumerWsService;
import com.spider.spider.consumer.response.AddressResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jian.Michael on 2017/4/3.
 */
@Controller
public class ConsumerWsController {

    private ConsumerWsService consumerWsService;

    @RequestMapping(value = "/addresslist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AddressResponse> getAddressListOfUser(String customerId) {
        return consumerWsService.getAddressListOfUser(Long.parseLong(customerId));
    }

    @RequestMapping(value = "/address/{addressId}/consumer/{customerId}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AddressResponse getAddress(@PathVariable String customerId, @PathVariable String addressId) {
        return consumerWsService.getAddress(Long.valueOf(customerId), Long.valueOf(addressId));
    }

    @RequestMapping(value = "/address/delete/{addressId}/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean deleteAddress(@PathVariable String customerId, @PathVariable String addressId) {
        return consumerWsService.deleteAddress(Long.parseLong(customerId), Long.parseLong(addressId));
    }

    @RequestMapping(value = "/address/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AddressResponse saveAddress(@RequestBody AddressResponse addressResponse) {
        return consumerWsService.saveAddress(addressResponse);
    }

    @Inject
    public void setConsumerWsService(ConsumerWsService consumerWsService) {
        this.consumerWsService = consumerWsService;
    }
}
