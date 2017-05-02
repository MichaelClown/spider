package com.spider.consumer.web;

import com.spider.common.annotation.Usage;
import com.spider.common.constant.SessionConstant;
import com.spider.consumer.service.ConsumerService;
import com.spider.spider.consumer.response.AddressResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String addressEdit() {

        return "";
    }

    @Usage("删除地址")
    @RequestMapping(value = "/address/{addressId}/delete", method = RequestMethod.GET)
    public String addressDelete(@PathVariable("addressId") String addressId) {

        return "";
    }

    @Usage("签收")
    @RequestMapping(value = "/{logisticsOrderId}/sign", method = RequestMethod.GET)
    public String sign(@PathVariable("logisticsOrderId") String logisticsOrderId) {

        return "";
    }

    @RequestMapping(value = "/myhome", method = RequestMethod.GET)
    public String myhome() {

        return "/consumer/myhome";
    }

    @Inject
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }
}
