package com.spider.common.api.client;

import com.alibaba.fastjson.JSON;
import com.spider.common.api.client.endpoint.SpiderEndPointBuilder;
import com.spider.common.http.Get;
import com.spider.common.http.SpiderHttpClient;
import com.spider.common.http.SpiderHttpResponse;
import com.spider.common.http.TextPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by jian.Michael on 2017/3/12.
 */
public final class SpiderApiClient {

    private final Logger logger = LoggerFactory.getLogger(SpiderApiClient.class);

    private SpiderHttpClient httpClient;

    public <T> T get(SpiderEndPointBuilder<T> builder) throws Exception {
        Get get = new Get(builder.getApiService());
        get.setAccept("application/json");
        return this.convertResponse(builder, httpClient.execute(get));
    }

    public <T> T post(SpiderEndPointBuilder<T> builder) throws Exception {
        TextPost post = new TextPost(builder.getApiService());
        post.setAccept("application/json");
        post.setContentType("application/json");
        if (StringUtils.hasText(builder.getBodyContent())) {
            post.setBody(builder.getBodyContent());
        }
        return this.convertResponse(builder, this.httpClient.execute(post));
    }

    private <T> T convertResponse(SpiderEndPointBuilder<T> builder, SpiderHttpResponse response) {
        String bodyContent = response.getResponseText();
        return JSON.parseObject(bodyContent, builder.getTargetClass());
    }

    @Resource(type = SpiderHttpClient.class)
    public void setHttpClient(SpiderHttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
