package com.spider;

 import com.spider.common.api.Apis;
 import com.spider.common.api.client.SpiderApiClient;
 import com.spider.common.api.client.endpoint.EndPointFactory;
 import com.spider.common.api.client.endpoint.ServiceGroups;
 import com.spider.common.api.util.XmlUtil;
 import com.spider.common.http.SpiderHttpClient;
 import com.spider.common.zookeeper.client.ServiceClient;
import com.spider.common.zookeeper.client.ZookeeperClient;
import com.spider.common.zookeeper.config.ServiceConfig;
import com.spider.common.zookeeper.constant.NameSpaceEnum;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
 import org.w3c.dom.Document;
 import org.xml.sax.SAXException;

 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;
 import java.io.IOException;

/**
 * Created by jian.Michael on 2017/1/25.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebConfig.class)
public class WebConfig extends BaseFacadeWebConfig {

    private final String zkAddress = System.getProperty("com.spider.zk.ip", "182.254.131.63:2181");
    private final String zkNameSpace = "/com/spider/cfg/1.0.0";
    private final String zkServiceNameSpace = "/com/spider/cfg/1.0.0/service";
    final String serviceDiscoverPath = zkServiceNameSpace + System.getProperty("com.spider.service.discover", "/spider-service");


    @Bean
    public ZookeeperClient zookeeperClient() {
        ZookeeperClient zookeeperClient = new ZookeeperClient(zkAddress, zkNameSpace, serviceDiscoverPath);
        zookeeperClient.iterator(NameSpaceEnum.RABBITMQ.getNameSpace(), NameSpaceEnum.RABBITMQ.getZkPath());
        zookeeperClient.iterator(NameSpaceEnum.DATABASE.getNameSpace(), NameSpaceEnum.DATABASE.getZkPath());
        return zookeeperClient;
    }

    @Bean
    public ServiceClient serviceClient() {
        ServiceConfig serviceConfig = new ServiceConfig()
                .serviceTypeEnum(ServiceConfig.ServiceTypeNum.CONSUMER);
        ServiceClient serviceClient = new ServiceClient(zkAddress, zkNameSpace, serviceDiscoverPath, serviceConfig);
        serviceClient.startListener();
        serviceClient.discoverService(false);
        return serviceClient;
    }

    @Bean
    public ServiceGroups serviceGroups() throws ParserConfigurationException, IOException, SAXException {
        ServiceGroups serviceGroups = null;
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        builderFactory.setNamespaceAware(false);
        Document document = builderFactory.newDocumentBuilder().parse(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("config/endpoint/config.xml"));
        serviceGroups = XmlUtil.fromXml(ServiceGroups.class, document);
        return serviceGroups;
    }

    @Bean
    public EndPointFactory endPointFactory() throws IOException, SAXException, ParserConfigurationException {
        EndPointFactory endPointFactory = new EndPointFactory();
        endPointFactory.setServiceClient(serviceClient());
        endPointFactory.setServiceGroups(this.serviceGroups());
        endPointFactory.setZookeeperClient(zookeeperClient());
        endPointFactory.initinalize();
        return endPointFactory;
    }

    @Bean
    public SpiderHttpClient spiderHttpClient() {
        return new SpiderHttpClient();
    }

    @Bean
    public SpiderApiClient spiderApiClient() {
        return new SpiderApiClient();
    }

    /**
     * Api封装 Bean
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @Bean
    public Apis apis() throws ParserConfigurationException, IOException, SAXException {
        Apis apis = null;
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        builderFactory.setNamespaceAware(false);
        Document document = builderFactory.newDocumentBuilder().parse(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("config/api/api_config.xml"));
        apis = XmlUtil.fromXml(Apis.class, document);
        apis.init();
        return apis;
    }


    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionFactory connectionFactory() {
        ZookeeperClient zkClient = zookeeperClient();
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/address"));
        factory.setPort(Integer.parseInt(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/port")));
        factory.setVirtualHost(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/vhosts"));
        factory.setUsername(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/username"));
        factory.setPassword(zkClient.get(NameSpaceEnum.RABBITMQ.getNameSpace(), "/common/rabbitmq/password"));
        return factory;
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        return new RabbitTemplate(connectionFactory());
    }


}
