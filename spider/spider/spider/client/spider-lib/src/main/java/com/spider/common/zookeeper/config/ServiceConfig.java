package com.spider.common.zookeeper.config;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public class ServiceConfig {

    private static final String DEFAULTZKBASEPATH = "/com/spider/cfg/1.0.0/service";

    private static final String DEFAULTSERVICEDISCOVERPATH = DEFAULTZKBASEPATH + "/spider-service";

    private ServiceTypeNum serviceTypeEnum;     //客户端类型(provider/consumer)

    private ServiceNameEnum serviceName;    //服务名称(outerservice/innerservice)

    public ServiceConfig() {

    }

    public ServiceTypeNum getServiceTypeEnum() {
        return serviceTypeEnum;
    }

    public ServiceConfig serviceTypeEnum(ServiceTypeNum serviceTypeEnum) {
        this.serviceTypeEnum = serviceTypeEnum;
        return this;
    }

    public ServiceNameEnum getServiceName() {
        return serviceName;
    }

    public ServiceConfig serviceName(ServiceNameEnum serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public enum  ServiceTypeNum {
        CONSUMER,
        PROVIDER;
    }

    public enum ServiceNameEnum {
        INNER,
        OUTER;
    }

    public String getServiceFullName() {
        return this.serviceName.name() + "_" + "SERVICE";
    }


}
