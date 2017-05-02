package com.spider.account.service;

import com.spider.account.repository.AccountRepository;
import com.spider.spider.account.response.UserAccountResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/5/2.
 */
@Service
public class AccountService {

    private AccountRepository accountRepository;

    public UserAccountResponse login(String cellPhone) {
        return accountRepository.login(cellPhone);
    }

    @Inject
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
