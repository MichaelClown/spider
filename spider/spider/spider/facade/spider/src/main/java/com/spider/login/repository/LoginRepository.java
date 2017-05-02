package com.spider.login.repository;

import com.spider.common.api.EndPoint;
import com.spider.common.api.client.SpiderApiClient;
import com.spider.common.api.client.endpoint.EndPointFactory;
import com.spider.common.api.client.endpoint.SpiderEndPointBuilder;
import com.spider.spider.account.response.UserAccountResponse;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by jian.Michael on 2017/4/2.
 */
@Repository
public class LoginRepository {

    private SpiderApiClient spiderApiClient;

    private EndPointFactory factory;

    // 登陆
    public UserAccountResponse login(String cellPhone, String identity) {
        return spiderApiClient.get(SpiderEndPointBuilder.create(UserAccountResponse.class)
                    .factory(factory).endpoint(EndPoint.INNER).action("/login?cellPhone=%s").arguments(cellPhone));
    }

    @Inject
    public void setFactory(EndPointFactory factory) {
        this.factory = factory;
    }

    @Inject
    public void setSpiderApiClient(SpiderApiClient spiderApiClient) {
        this.spiderApiClient = spiderApiClient;
    }
}
