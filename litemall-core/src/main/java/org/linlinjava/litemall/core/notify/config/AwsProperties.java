package org.linlinjava.litemall.core.notify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/21 15:59
 * @description：TODO
 */
@ConfigurationProperties(prefix = "litemall.aws")
public class AwsProperties {
    private Boolean enable;
    private String accessKey;
    private String secretKey;
    private String region;
    private String appId;
    private String originationNumber;
    private Email email;
    private Sms sms;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOriginationNumber() {
        return originationNumber;
    }

    public void setOriginationNumber(String originationNumber) {
        this.originationNumber = originationNumber;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public static class Email{
        private boolean enable;
        private Map<String, String> template;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public Map<String, String> getTemplate() {
            return template;
        }

        public void setTemplate(Map<String, String> template) {
            this.template = template;
        }
    }

    public static class Sms{
        private boolean enable;
        private Map<String, String> template;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public Map<String, String> getTemplate() {
            return template;
        }

        public void setTemplate(Map<String, String> template) {
            this.template = template;
        }
    }
}
