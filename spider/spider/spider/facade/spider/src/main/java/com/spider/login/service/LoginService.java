package com.spider.login.service;

import com.spider.login.repository.LoginRepository;
import com.spider.spider.account.response.UserAccountResponse;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/4/2.
 */
@Service
public class LoginService {

    private LoginRepository loginRepository;

    public UserAccountResponse login(String cellPhone, String identity) {
        return loginRepository.login(cellPhone, identity);
    }

    @Inject
    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
}
