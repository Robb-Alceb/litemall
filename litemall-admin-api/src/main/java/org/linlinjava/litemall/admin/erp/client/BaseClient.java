package org.linlinjava.litemall.admin.erp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：stephen
 * @date ：Created in 2020/7/1 16:23
 * @description：TODO
 */
public abstract class BaseClient {
    @Autowired
    private RestTemplate restTemplate;
    protected static HttpHeaders headers;

    static {
        headers = new HttpHeaders();
        headers.set("tenantId","63");
        headers.set("lumiere_erp_123", "lumiere_erp_123");
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
