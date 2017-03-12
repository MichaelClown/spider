package com.spider.common.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by jian.Michael on 2017/3/12.
 */
public class TextPost extends SpiderHttpRequest {

    private String body;

    private String contentType = "text/plain";

    private boolean chunked;

    public TextPost(String url) {
        super(url);
    }

    @Override
    HttpRequestBase toHttpRequest() throws IOException {
        HttpPost post = new HttpPost(this.url);
        if (StringUtils.hasText(this.body)) {
            StringEntity entity = new StringEntity(this.body, "UTF-8");
            entity.setContentType(this.contentType + "; charset = UTF-8");
            entity.setChunked(chunked);
            post.setEntity(entity);
        }

        return post;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }
}
