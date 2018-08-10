package spring.boot.swagger2ui.autoconfigure.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.boot.swagger2ui.autoconfigure.Swagger2AutoConfiguration;
import swagger2.web.http.servlet.SwaggerResourceServlet;

/**
 * 描述: swagger2ui 自动配置
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/10
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(Swagger2UIProperties.class)//自动引入自定义配置类
@AutoConfigureAfter(Swagger2AutoConfiguration.class)
public class Swagger2UIAutoConfiguration {
    @Autowired
    private Swagger2UIProperties properties;

    private final String DEFAULT_URL_PATH = "/swagger2-ui";


    @Bean("swagger2UIServlet")
    @ConditionalOnBean
    @ConditionalOnClass(swagger2.web.http.servlet.SwaggerResourceServlet.class)//SwaggerResourceServlet存在时才会装载该类
    @ConditionalOnMissingBean(type = {"swagger2.web.http.servlet.SwaggerResourceServlet"})//如果已经定义改类则此处不会再生成bean
    public ServletRegistrationBean registrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new SwaggerResourceServlet());
        String url = properties.getBasePath() != null ? properties.getBasePath() : DEFAULT_URL_PATH;
        servletRegistrationBean.addUrlMappings(url + "/*");
        return servletRegistrationBean;
    }
}
