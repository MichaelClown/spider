package com.spider.common.zookeeper.client;

import com.spider.common.zookeeper.config.ServiceConfig;
import com.spider.common.zookeeper.domain.ServiceDetail;
import com.spider.common.zookeeper.domain.ServiceGroup;
import com.spider.common.zookeeper.listener.ServicePathChildrenCacheListener;
import com.spider.common.zookeeper.manager.AbstractZookeeperFeature;
import com.spider.common.zookeeper.manager.AbstractZookeeperFeature.IServiceClient;
import com.spider.common.zookeeper.listener.service.discover.ServiceDiscoverStateListener;
import com.spider.common.zookeeper.listener.service.register.ServiceRegistStateListener;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jian.Michael on 2017/1/29.
 */
public class ServiceClient extends AbstractZookeeperFeature implements IServiceClient {

    private final String serverAddress = System.getProperty("com.spider.service.address");

    private Map<String, ServiceGroup> serviceGroupContainer = new ConcurrentHashMap<>();

    private ServiceConfig serviceConfig;

    public ServiceClient(String zkAddress, String zkNameSpace, String serviceDiscoverPath, ServiceConfig serviceConfig) {
        super(zkAddress, zkNameSpace, serviceDiscoverPath);
        this.serviceConfig = serviceConfig;
    }

    @Override
    public Map<String, ServiceGroup> getServiceGroupContainer() {
        return this.serviceGroupContainer;
    }

    @Override
    public ServiceDetail loadBalance(String serviceName) {



        return null;
    }


    @Override
    public void registService() {
        if (this.serviceConfig.getServiceTypeEnum().equals(ServiceConfig.ServiceTypeNum.PROVIDER)) {
            String servicePath = this.pathConfig.getFullDiscoverPath("/" + this.serviceConfig.getServiceFullName());
            this.createServiceNode(servicePath, serverAddress);
        }
    }

    @Override
    public void discoverService(boolean reset, String... var2) {
        if (this.serviceConfig.getServiceTypeEnum().equals(ServiceConfig.ServiceTypeNum.CONSUMER)) {
            List<String> children = this.zookeeperManager.getChildrenNode(this.pathConfig.getServiceDiscoverPath());
            if (!CollectionUtils.isEmpty(children)) {
                Iterator iterator = children.iterator();
                while (iterator.hasNext()) {
                    String serviceName = (String) iterator.next();
                    handlerService(reset, serviceName);
                }
            }
        }
    }

    private void handlerService(boolean reset, String serviceName) {
        if (!reset) {
            String serviceFullPath = this.pathConfig.getFullDiscoverPath("/" + serviceName);
            ServicePathChildrenCacheListener listener = new ServicePathChildrenCacheListener(serviceName, serviceFullPath, this);
            this.zookeeperManager.addPathChildrenListener(serviceFullPath, listener);
        }

        ServiceGroup serviceGroup = this.serviceGroupContainer.get(serviceName);
        if (serviceGroup == null) {
            serviceGroup = new ServiceGroup(serviceName);
            this.serviceGroupContainer.put(serviceName, serviceGroup);
        }

        List<String> services = this.zookeeperManager.getChildrenNode(this.pathConfig.getFullDiscoverPath("/" + serviceName));
        this.loadService(serviceName, services, serviceGroup);
    }

    @Override
    public void startListener() {
        switch (serviceConfig.getServiceTypeEnum()) {
            case PROVIDER:
                ServiceRegistStateListener serviceRegistStateListener = new ServiceRegistStateListener(this);
                this.zookeeperManager.addServiceStateListener("SERVICEREGISTLISTENER", serviceRegistStateListener);
                break;
            case CONSUMER:
                ServiceDiscoverStateListener serviceDiscoverStateListener = new ServiceDiscoverStateListener(this);
                this.zookeeperManager.addServiceStateListener("SERVICEDISCOVERYLISTENER", serviceDiscoverStateListener);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean checkHealth(String path) {
        return this.zookeeperManager.checkIfExist(path);
    }

    private void createServiceNode(String path, String serverAddress) {
        String serverPath = path + "/" + this.getServerPath(serverAddress);
        if (this.zookeeperManager.checkIfExist(serverPath)) {
            this.zookeeperManager.delete(serverPath, false);
        }
        this.zookeeperManager.createWithData(serverPath, serverAddress, true);
    }

    private String getServerPath(String serverAddress) {
        return "SERVER-" + serverAddress.replaceAll("\\.", "").trim();
    }

    private void loadService(String serviceName, List<String> services, ServiceGroup serviceGroup) {
        if (!CollectionUtils.isEmpty(services)) {
            Iterator iterator = services.iterator();

            while (iterator.hasNext()) {
                String serviceNodeName = (String) iterator.next();
                try {
                    String serviceNodeValue = this.zookeeperManager.readString(this.pathConfig.getFullDiscoverPath("/" + serviceName + "/" + serviceNodeName));
                    ServiceDetail serviceDetail = ServiceDetail.parseZKValue(serviceNodeValue);
                    serviceGroup.put(serviceNodeName, serviceDetail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
