package com.spider.login.web;

import com.spider.common.constant.SessionConstant;
import com.spider.common.constant.UserTypeItems;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jian.Michael on 2017/5/2.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String homePage(HttpServletRequest httpServletRequest, Map<String, Object> map) {
        String userType = httpServletRequest.getSession().getAttribute(SessionConstant.USER_TYPE).toString();
        String userName = httpServletRequest.getSession().getAttribute(SessionConstant.USER_NAME).toString();
        if (UserTypeItems.PERSONAL_USER.name().equals(userType)) {
            map.put("userName", userName);
            return "forward:/consumer/myhome";
        } else {
            return "";
        }
    }

}
