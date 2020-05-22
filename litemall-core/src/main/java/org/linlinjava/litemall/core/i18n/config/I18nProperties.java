package org.linlinjava.litemall.core.i18n.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/20 16:15
 * @description：TODO
 */
@Configuration
@ConfigurationProperties(prefix = "litemall.i18n")
public class I18nProperties {
    private boolean enable;

    private Map<String, List<String>> classes = new HashMap<>();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Map<String, List<String>> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, List<String>> classes) {
        this.classes = classes;
    }

}
