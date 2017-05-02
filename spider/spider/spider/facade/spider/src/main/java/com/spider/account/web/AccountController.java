package com.spider.account.web;

import com.spider.account.domain.Account;
import com.spider.common.annotation.Usage;
import com.spider.common.constant.SessionConstant;
import com.spider.common.constant.UserTypeItems;
import com.spider.login.service.LoginService;
import com.spider.spider.account.response.UserAccountResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jian.Michael on 2017/4/1.
 */
@Usage("用户账号相关")
@Controller
@RequestMapping("/account")
public class AccountController {

    private LoginService loginService;

    @Usage("跳转登录页面")
    @RequestMapping(value = "/toLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public String toLogin(Map<String, Object> map, String message) {
        map.put("message", message);
        return "account/login";
    }

    @Usage("登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Account account, Map<String, Object> map, HttpServletRequest httpServletRequest) {
        UserAccountResponse response = loginService.login(account.getCellPhone(), account.getIdentity());
        if (response != null && response.getCustomerId() != null) {
            httpServletRequest.getSession().setAttribute(SessionConstant.USER_NAME, response.getUserName());
            httpServletRequest.getSession().setAttribute(SessionConstant.USER_ID, response.getCustomerId());
            httpServletRequest.getSession().setAttribute(SessionConstant.USER_TYPE, response.getType());
            return "forward:/index";
        } else {
            map.put("message", "用户不存在");
            return "forward:/account/toLogin";
        }
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
