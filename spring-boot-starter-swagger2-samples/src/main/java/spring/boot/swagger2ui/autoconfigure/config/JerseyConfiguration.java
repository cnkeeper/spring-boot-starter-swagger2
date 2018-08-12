package spring.boot.swagger2ui.autoconfigure.config;

import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;

import javax.annotation.PostConstruct;

/**
 * 描述: jersey配置
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/12
 */
@Configuration
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        packages("spring.boot.swagger2ui.autoconfigure.resources");

    }


    /**
     * swagger2-ui.enable为false 时应当手动配置，打开@PostConstruct注解
     */
    //@PostConstruct
    public void configureSwagger() {
        // Available at localhost:port/swagger.json
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("springboot-jersey-swagger-example");
        config.setTitle("Spring Boot + Jersey + Swagger + Example");
        config.setVersion("v1");
        config.setContact("LeiLi.Zhang");
        config.setSchemes(new String[]{"http"/*, "https"*/});
        config.setBasePath("");
        config.setResourcePackage("spring.boot.swagger2ui.autoconfigure.resources");
        config.setPrettyPrint(true);
        config.setScan(true);
    }

    /**
     * 使用spring提供的CorsFilter bean完成同源策略的问题解决
     * @return
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
