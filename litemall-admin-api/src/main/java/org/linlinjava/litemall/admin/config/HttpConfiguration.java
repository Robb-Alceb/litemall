package org.linlinjava.litemall.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：stephen
 * @date ：Created in 2020/6/4 10:39
 * @description：TODO
 */
@Configuration
public class HttpConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
