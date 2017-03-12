package com.spider.common.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by jian.Michael on 2017/1/31.
 */
public class SpiderHttpClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int DEFAULT_TIME_OUT = 2000;

    private DefaultHttpClient client;

    private HttpParams params;

    private static PoolingClientConnectionManager manager;

    public SpiderHttpClient() {
        this.params = new SyncBasicHttpParams();
        this.params.setIntParameter("http.connection.timeout", DEFAULT_TIME_OUT);
        this.params.setIntParameter("http.socket.timeout", DEFAULT_TIME_OUT);
        DefaultHttpClient.setDefaultHttpParams(params);

    }

    private synchronized DefaultHttpClient getHttpClient() {
        if (manager == null) {
            manager = new PoolingClientConnectionManager();
            manager.setMaxTotal(200);
            manager.setDefaultMaxPerRoute(20);
        }
        if (client == null) {
            this.client = new DefaultHttpClient(this.manager, this.params);
        }

        return this.client;
    }

    public SpiderHttpResponse execute(SpiderHttpRequest request) {

        SpiderHttpResponse spiderHttpResponse = null;

        try {
            HttpResponse response = this.executeRequest(request);
            String responseText = this.readResponseText(response);
            SpiderHttpHeaders headers = SpiderHttpHeaders.createResponseHeaders(response);
            SpiderHttpStatusCode statusCode = new SpiderHttpStatusCode(response.getStatusLine().getStatusCode());
            spiderHttpResponse = new SpiderHttpResponse(statusCode, headers, responseText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return spiderHttpResponse;
    }

    public SpiderHttpBinaryResponse download(SpiderHttpRequest request) {
        SpiderHttpBinaryResponse spiderResponse = null;

        try {
            HttpResponse response = this.executeRequest(request);
            byte[] responseBytes = this.readResponseBytes(response);
            SpiderHttpStatusCode statusCode = new SpiderHttpStatusCode(response.getStatusLine().getStatusCode());
            SpiderHttpHeaders headers = SpiderHttpHeaders.createResponseHeaders(response);
            spiderResponse = new SpiderHttpBinaryResponse(statusCode, headers, responseBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spiderResponse;
    }

    private HttpResponse executeRequest(SpiderHttpRequest spiderHttpRequest) throws IOException {

        HttpRequestBase request = spiderHttpRequest.toHttpRequest();

        HttpResponse response = getHttpClient().execute(request);

        return response;
    }

    private String readResponseText(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String responseText = EntityUtils.toString(entity, "UTF-8");
        return responseText;
    }

    private byte[] readResponseBytes(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        byte[] responseBytes = EntityUtils.toByteArray(entity);
        return responseBytes;
    }

}
