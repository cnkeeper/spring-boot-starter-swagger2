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

    private String basePath;

    private  Boolean enable;

    private String configId = "ConfigID";

    private String title = "Springboot-jersey-swagger2";

    private String version = "v1";

    private String contact = "Contact";

    private String resourcePackage;


    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getResourcePackage() {
        return resourcePackage;
    }

    public void setResourcePackage(String resourcePackage) {
        this.resourcePackage = resourcePackage;
    }
}
