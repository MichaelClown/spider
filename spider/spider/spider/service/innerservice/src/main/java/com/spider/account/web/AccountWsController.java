package com.spider.account.web;

import com.spider.account.service.AccountWsService;
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
public class AccountWsController {

    private AccountWsService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserAccountResponse login(@RequestParam String cellPhone) {
        return accountService.login(cellPhone);
    }

    @Inject
    public void setAccountService(AccountWsService accountService) {
        this.accountService = accountService;
    }
}
