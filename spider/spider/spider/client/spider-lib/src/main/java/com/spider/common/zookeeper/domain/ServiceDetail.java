package com.spider.common.zookeeper.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public class ServiceDetail {

    private String protocol;

    private String serviceIp;

    private Integer servicePort;

    public ServiceDetail() {
    }

    public ServiceDetail(String protocol, String serviceIp, Integer servicePort) {
        this.protocol = protocol;
        this.serviceIp = serviceIp;
        this.servicePort = servicePort;
    }

    public String getProtocol() {
        return protocol;
    }

    public ServiceDetail protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    private static boolean checkProtocol(String protocol) {
        return ("http".equals(protocol.toLowerCase()) || "https".equals(protocol.toLowerCase())) ? true : false;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public ServiceDetail serviceIp(String serviceIp) {
        this.serviceIp = serviceIp;
        return this;
    }

    private static boolean checkIp(String ipAddress) {
        Pattern pattern = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$");
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public Integer getServicePort() {
        return servicePort;
    }

    public ServiceDetail servicePort(Integer servicePort) {
        this.servicePort = servicePort;
        return this;
    }

    private static boolean checkPort(Integer servicePort) {
        return (servicePort > 1 && servicePort < 65535) ? true : false;
    }

    @Override
    public String toString() {
        return protocol
                + '-'
                + serviceIp
                + '-'
                + servicePort;
    }

    public static ServiceDetail parseZKValue(String serviceZKValue) throws Exception {
        String[] strs = serviceZKValue.split("-");
        if (strs.length == 3 && checkProtocol(strs[0]) && checkIp(strs[1]) && checkPort(Integer.parseInt(strs[2]))) {
            ServiceDetail serviceDetail = new ServiceDetail()
                    .protocol(strs[0])
                    .serviceIp(strs[1])
                    .servicePort(Integer.parseInt(strs[2]));
            return serviceDetail;
        } else {
            throw new Exception("服务注册格式错误 : " + serviceZKValue);
        }
    }
}
