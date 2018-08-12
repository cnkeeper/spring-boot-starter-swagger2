package spring.boot.swagger2ui.autoconfigure;

import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import spring.boot.swagger2ui.autoconfigure.ui.Swagger2UIProperties;

import javax.annotation.PostConstruct;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/10
 */
@Configuration
@EnableConfigurationProperties(Swagger2UIProperties.class)
public class Swagger2AutoConfiguration implements ApplicationContextAware {

    @Autowired
    private Swagger2UIProperties properties;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void injectSwagger2() {
        ResourceConfig resourceConfig = null;
        try {
            resourceConfig = this.applicationContext.getBean(ResourceConfig.class);
        } catch (Exception e) {
            // ignore
        }

        if (null != resourceConfig && properties.getEnable()) {
            resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class).
                    register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
            BeanConfig config = new BeanConfig();
            config.setConfigId(properties.getConfigId());
            config.setTitle(properties.getTitle());
            config.setVersion(properties.getVersion());
            config.setContact(properties.getContact());
            config.setSchemes(new String[]{"http", "https"});
            config.setBasePath(properties.getBasePath());
            config.setResourcePackage(properties.getResourcePackage());
            config.setPrettyPrint(true);
            config.setScan(true);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
