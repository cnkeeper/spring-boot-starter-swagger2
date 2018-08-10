package spring.boot.swagger2ui.autoconfigure.ui;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述: Swagger2UI 配置
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/10
 */
@ConfigurationProperties(prefix = Swagger2UIProperties.SWAGGER2UI_PREFIX)
public class Swagger2UIProperties {
    public static final String SWAGGER2UI_PREFIX = "swagger2-ui";

    private String urlPath;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
