import com.spider.common.zookeeper.client.ServiceClient;
import com.spider.common.zookeeper.client.ZookeeperClient;
import com.spider.common.zookeeper.config.ServiceConfig;
import com.spider.common.zookeeper.constant.NameSpaceEnum;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by jian.Michael on 2017/1/26.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebConfig.class)
public class WebConfig extends DbConfig {

    final String zkAddress = "182.254.131.63:2181";
    final String zkServiceNameSpace = "/com/spider/cfg/1.0.0/service";
    final String zkNameSpace = "/com/spider/cfg/1.0.0";
    final String serviceDiscoverPath = zkServiceNameSpace + System.getProperty("com.spider.service.discover", "/spider-service");

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ZookeeperClient zookeeperClient() {
        ZookeeperClient zookeeperClient = new ZookeeperClient(zkAddress, zkNameSpace, serviceDiscoverPath);
        zookeeperClient.iterator(NameSpaceEnum.RABBITMQ.getNameSpace(), NameSpaceEnum.RABBITMQ.getZkPath());
        zookeeperClient.iterator(NameSpaceEnum.DATABASE.getNameSpace(), NameSpaceEnum.DATABASE.getZkPath());
        zookeeperClient.iterator(NameSpaceEnum.QUEUE.getNameSpace(), NameSpaceEnum.QUEUE.getZkPath());
        return zookeeperClient;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ServiceClient serviceClient() {
        ServiceConfig serviceConfig = new ServiceConfig()
                .serviceName(ServiceConfig.ServiceNameEnum.INNER)
                .serviceTypeEnum(ServiceConfig.ServiceTypeNum.PROVIDER);
        ServiceClient serviceClient = new ServiceClient(zkAddress, zkNameSpace, serviceDiscoverPath, serviceConfig);
        serviceClient.startListener();
        serviceClient.registService();
        return serviceClient;
    }

    private String getZKValue(String nameSpace, String zkPath, String defaultValue) {
        String value = zookeeperClient().get(nameSpace, zkPath);
        return StringUtils.hasText(value) ? value : defaultValue;
    }
}
