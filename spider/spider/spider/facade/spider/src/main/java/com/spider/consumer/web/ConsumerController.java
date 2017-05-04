package com.spider.consumer.web;

import com.spider.common.annotation.Usage;
import com.spider.common.constant.SessionConstant;
import com.spider.consumer.service.ConsumerService;
import com.spider.logistics.service.LogisticsService;
import com.spider.spider.consumer.response.AddressResponse;
import com.spider.spider.logistics.LogisticsOrderStatus;
import com.spider.spider.order.OrderRecordItem;
import com.spider.spider.order.OrderResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        return "/consumer/address_list";
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
        return "/consumer/address_detail";
    }

    @Usage("保存地址")
    @RequestMapping(value = "/address/edit", method = RequestMethod.POST)
    public String addressSave(AddressResponse addressResponse, HttpServletRequest request, Map<String, Object> map) {
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
    public String sign(@PathVariable("logisticsOrderId") String logisticsOrderId, HttpServletRequest request, Map<String, Object> map) {
        Long customerId = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
        Boolean result = logisticsService.sign(logisticsOrderId, customerId);
        map.put("result", result);
        return "";
    }

    @Usage("用户个人中心")
    @RequestMapping(value = "/myhome", method = RequestMethod.GET)
    public String myhome() {

        return "/consumer/myhome";
    }

    @Usage("订单列表")
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public String orderList(Map<String, Object> map, HttpServletRequest request) {
        Long customerId = (Long) request.getSession().getAttribute(SessionConstant.USER_ID);
        List<OrderResponse> orderListAll = consumerService.getOrderListOfUser(customerId);
        List<OrderResponse> orderListDone = new ArrayList<>();
        List<OrderResponse> orderListDoing = new ArrayList<>();
        buildOrderListDone(orderListAll, orderListDone, orderListDoing);
        map.put("orderListDone", orderListDone);
        map.put("orderListDoing", orderListDoing);
        return "/consumer/order_list";
    }

    @Usage("订单详情")
    @RequestMapping(value = "/order/{orderId}/detail")
    public String orderDetail(Map<String, Object> map, HttpServletRequest request, @PathVariable String orderId) {
        OrderResponse orderResponse = consumerService.getOrderDetailByOrderId(Long.parseLong(orderId));
        List<OrderRecordItem> orderRecordList = consumerService.getOrderRecordList(Long.parseLong(orderId));
        orderResponse.setOrderRecords(orderRecordList);

        map.put("orderDetail", orderResponse);
        return "/consumer/address_detail";
    }

    private void buildOrderListDone(List<OrderResponse> orderListAll, List<OrderResponse> orderListDone, List<OrderResponse> orderListDoing) {
        if (!CollectionUtils.isEmpty(orderListAll)) {
            for (OrderResponse orderResponse : orderListAll) {
                if (LogisticsOrderStatus.ALREADY_SIGN.name().equals(orderResponse.getLogisticsOrderStatus()) || LogisticsOrderStatus.COMPLETED.name().equals(orderResponse.getLogisticsOrderStatus())) {
                    orderListDone.add(orderResponse);
                } else {
                    orderListDoing.add(orderResponse);
                }
            }
        }
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
