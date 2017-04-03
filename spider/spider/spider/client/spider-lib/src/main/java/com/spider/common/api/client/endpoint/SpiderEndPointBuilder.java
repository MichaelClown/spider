package com.spider.common.api.client.endpoint;

import com.spider.common.api.Api;
import com.spider.common.api.Apis;
import com.spider.common.zookeeper.domain.ServiceDetail;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by jian.Michael on 2017/3/12.
 */
public final class SpiderEndPointBuilder<T> {

    private Class<T> targetClass;

    private Class<?>[] elementClass;

    private Enum endPoint;

    private String action;

    private String bodyContent;

    private EndPointFactory factory;

    private Apis apis;

    private String apiName;

    private SpiderEndPointBuilder() {

    }

    public static <T> SpiderEndPointBuilder<T> create(Class<T> targetClass) {
        if (null == targetClass) {
            return null;
        } else {
            SpiderEndPointBuilder builder = new SpiderEndPointBuilder();
            builder.target(targetClass);
            return builder;

        }
    }

    public SpiderEndPointBuilder<T> elementTypes(Class... elementClass) {
        this.elementClass = elementClass;
        return this;
    }

    public SpiderEndPointBuilder<T> action(String action) {
        this.action = action;
        return this;
    }

    public SpiderEndPointBuilder<T> body(String bodyContent) {
        this.bodyContent = bodyContent;
        return this;
    }

    public SpiderEndPointBuilder<T> arguments(String... arguments) {
        this.action(String.format(this.action, arguments));
        return this;
    }

    public SpiderEndPointBuilder<T> factory(EndPointFactory factory) {
        this.factory = factory;
        return this;
    }

    public SpiderEndPointBuilder<T> apis(Apis apis) {
        this.apis = apis;
        return this;
    }

    public SpiderEndPointBuilder<T> thirdApi(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public SpiderEndPointBuilder<T> endpoint(Enum endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    /**
     * 获取api url地址
     * @return
     */
    public String getApiService() throws Exception {
        StringBuilder builder = new StringBuilder("http://");
        if (!StringUtils.hasText(this.action)) {
            throw new Exception("action is null");
        } else if (null == this.endPoint) {
            throw new Exception("endpoint is null");
        } else if (null == factory) {
            throw new Exception("factor is null");
        } else {
            builder.append(this.getEndPoint()).append(this.action);
        }

        if (StringUtils.hasText(this.apiName)) {
            if (null == apis) {
                throw new Exception("apis is null");
            }
            Api apiObj = this.apis.getApi(this.apiName);
            if (null == apiObj) {
                throw new Exception("api not found");
            } else {
                builder.append(builder.indexOf("&") > 0 ? "&" : "?");
                builder.append("api=").append(apiObj.getEndpoint());
            }
        }

        return builder.toString();
    }

    private String getEndPoint() throws Exception {
        Map<String, EndPoint> underGroupEndPoints = this.factory.getDefaultServiceGroups();
        if (CollectionUtils.isEmpty(underGroupEndPoints)) {
            throw new Exception("Group[service] not exists");
        } else {
            String endPointName = this.endPoint.name();
            EndPoint endPoint = underGroupEndPoints.get(endPointName);
            if (null == endPoint) {
                throw new Exception("分组[service]下不存在[" + endPointName + "]的服务");
            } else {
                String context = endPoint.getContext();
//                ServiceDetail serviceDetail = this.factory.getServiceClient().loadBalance(this.endPoint.name());
                ServiceDetail serviceDetail = new ServiceDetail("http", "127.0.0.1", 8988);
                String host = serviceDetail.getServiceIp();
                Integer port = serviceDetail.getServicePort();
                String url = host + ":" + port;
                return StringUtils.hasText(context) ? url + context : url;
            }
        }
    }


    private SpiderEndPointBuilder<T> target(Class<T> targetClass) {
        this.targetClass = targetClass;
        return this;
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }

    public String getBodyContent() {
        return bodyContent;
    }
}