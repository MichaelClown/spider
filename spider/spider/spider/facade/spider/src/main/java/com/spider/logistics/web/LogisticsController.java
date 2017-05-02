package com.spider.logistics.web;

import com.spider.common.annotation.Usage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by jian.Michael on 2017/3/30.
 */
@Usage("物流管理模块")
@Controller
@RequestMapping("/logistics")
public class LogisticsController {

    @Usage("物流记录查询")
    @RequestMapping(value = "/record/{orderId}", method = RequestMethod.GET)
    public String logisticsRecord(@PathVariable("orderId") String orderId, Map<String, Object> map) {

        return "/";
    }

    @Usage("生成物流单")
    @RequestMapping(value = "/note/generate", method = RequestMethod.POST)
    public void logisticsNoteGenerate() {


    }

    @Usage("物流单创建")
    @RequestMapping(value = "/note/entry", method = RequestMethod.GET)
    public String toStartLogistics() {

        return "";
    }

}
