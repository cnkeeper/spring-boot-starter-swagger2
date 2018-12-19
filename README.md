# swagger2 的springboot版本  

# 功能介绍  
  为使用Jersey替代Springmvc作为控制层的springboot项目提供swagger2自动集成支持(UI + API).    
  ** 已通过Apache2.0 License 开源，欢迎大家积极完善**
# 项目介绍
> spring-boot-starter-swagger2  
>* spring-boot-starter-swagger2: 根项目  
>* spring-boot-swagger2-autoconfigure: springboot集成    
>* swagger2-web: ui页面集成  
>* swagger2-ui: 静态页面  
>* spring-boot-starter-swagger2-samples: 示例  


# 快速开始  
## 1.安装依赖到本地  
> mvn clean -Dmaven.test.skip=true package install  

## 2.新建springboot项目  

## 3.导入依赖  
```xml
        <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-jersey</artifactId>
                    <exclusions>
                        <exclusion>
                            <artifactId>spring-boot-starter-tomcat</artifactId>
                            <groupId>org.springframework.boot</groupId>
                        </exclusion>
                        <exclusion>
                            <artifactId>spring-boot-starter-logging</artifactId>
                            <groupId>org.springframework.boot</groupId>
                        </exclusion>
                    </exclusions>
        </dependency>
        <dependency>
            <groupId>com.github.TIME69</groupId>
            <artifactId>spring-boot-starter-swagger2</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```    
## 4.编写接口  
```java
package spring.boot.swagger2ui.autoconfigure.resources;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.stereotype.Component;
import spring.boot.swagger2ui.autoconfigure.entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("hello")
@Api(value="Jersey-HelloWorld API 列表")
public class HelloResource {

    Logger logger = LoggerFactory.getLogger(HelloResource.class);

    private static Map<Integer, User> INIT_DATA;

    static {
        INIT_DATA = new HashMap<Integer, User>();
        final int count = 100;
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(Integer.toString(i));
            user.setName("name:" + i);
            INIT_DATA.put(i, user);
        }
    }

    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "根据id查询信息",notes = "查询信息",response = User.class)
    @ApiImplicitParam(name = "id",value = "id",dataType = "String",paramType = "query",example = "1112")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    public User getUserById(@PathParam("id") Integer id) {
        System.out.println(id);
        return INIT_DATA.get(id);
    }
}

```
## 5.配置swagger2  

### 方式一、自动集成至Jersey
> 配置application.yml
```xml
#swagger配置
swagger2-ui:
  enable: true #是否自动集成至jersey
  resourcePackage: spring.boot.swagger2ui.autoconfigure.resources #API接口包
  basePath: /swagger-ui #页面访问url

```
  
### 方式二、手动集成至Jersey  
> 配置application.yml  
```xml
#swagger配置
swagger2-ui:
  enable: false #是否自动集成至jersey
```
> 在Jersey的配置类中增加如下代码：  
```java
    /**
     * swagger2-ui.enable为false 时应当手动配置，打开@PostConstruct注解
     */
    @PostConstruct
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
```

## 6.访问接口  
> http://{ip}:{port}/{context-path}/{swagger2-ui.basePath}/  
注：这里{**}为application.yml中对应的配置值
