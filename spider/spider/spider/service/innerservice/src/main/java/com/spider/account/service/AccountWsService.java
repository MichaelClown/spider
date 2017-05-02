package com.spider.account.service;

import com.spider.account.repository.AccountWsRepository;
import com.spider.spider.account.response.UserAccountResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/5/2.
 */
@Service
public class AccountWsService {

    private AccountWsRepository accountRepository;

    public UserAccountResponse login(String cellPhone) {
        return accountRepository.login(cellPhone);
    }

    @Inject
    public void setAccountRepository(AccountWsRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
