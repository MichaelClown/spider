package com.spider.account.web;

import com.spider.account.service.AccountService;
import com.spider.spider.account.response.UserAccountResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/4/2.
 */
@Controller
public class AccountController {

    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserAccountResponse login(@RequestParam String cellPhone) {
        return accountService.login(cellPhone);
    }

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
