package com.spider.consumer.web;

import com.spider.common.annotation.Usage;
import com.spider.common.constant.SessionConstant;
import com.spider.consumer.service.ConsumerService;
import com.spider.logistics.service.LogisticsService;
import com.spider.spider.consumer.response.AddressResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by jian.Michael on 2017/3/30.
 */
@Usage("用户模块")
@Controller
@RequestMapping("/consumer")
public class ConsumerController {

    private ConsumerService consumerService;

    private LogisticsService logisticsService;

    @Usage("地址列表")
    @RequestMapping(value = "/address/list", method = RequestMethod.GET)
    public String addressList(HttpServletRequest request, Map<String, Object> map) {
        Long customerId = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
        List<AddressResponse> addressList = consumerService.getAddressListOfUser(customerId);
        map.put("addressList", addressList);
        return "";
    }

    @Usage("编辑地址")
    @RequestMapping(value = "/address/edit", method = RequestMethod.GET)
    public String addressEdit(@RequestParam(required = false) String id, HttpServletRequest request, Map<String, Object> map) {
        Long customerId = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
        AddressResponse addressResponse = new AddressResponse();
        if (StringUtils.hasText(id)) {
            addressResponse = consumerService.getAddress(customerId, Long.parseLong(id));
        }
        map.put("address", addressResponse);
        return "";
    }

    @Usage("保存地址")
    @RequestMapping(value = "/address/edit", method = RequestMethod.POST)
    public String addressSave(@RequestBody AddressResponse addressResponse, HttpServletRequest request, Map<String, Object> map) {
        Long customerId = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
        addressResponse.setCustomerId(customerId);
        AddressResponse response = consumerService.saveAddress(addressResponse);
        return this.addressEdit(response.getAddressId().toString(), request, map);
    }

    @Usage("删除地址")
    @RequestMapping(value = "/address/{addressId}/delete", method = RequestMethod.GET)
    public String addressDelete(@PathVariable("addressId") String addressId, HttpServletRequest request, Map<String, Object> map) {
        Long customerId = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
        AddressResponse addressResponse = consumerService.getAddress(customerId, Long.parseLong(addressId));
        Boolean result;
        String message;
        if (addressResponse == null || addressResponse.getAddressId() == null) {
            result = false;
            message = "该地址已被删除，请重试";
        } else {
            result = consumerService.deleteAddress(customerId, Long.valueOf(addressId));
            message = result ? "删除成功" : "删除失败";
        }
        map.put("result", result);
        map.put("message", message);
        return addressList(request, map);
    }

    @Usage("签收")
    @RequestMapping(value = "/{logisticsOrderId}/sign", method = RequestMethod.GET)
    public String sign(@PathVariable("logisticsOrderId") String logisticsOrderId, HttpServletRequest request) {
        Long customerId = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
        Boolean result = logisticsService.sign(logisticsOrderId, customerId);
        return "";
    }

    @RequestMapping(value = "/myhome", method = RequestMethod.GET)
    public String myhome() {

        return "/consumer/myhome";
    }

    @Inject
    public void setLogisticsService(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @Inject
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }
}
