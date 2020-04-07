package org.linlinjava.litemall.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/7 15:03
 * @description：TODO
 */
@Configuration
public class LitemallMultipartConfig {
    @Value("${litemall.upload.max-file-size}")
    private String maxFileSize;
    @Value("${litemall.upload.max-request-size}")
    private String maxRequestSize;


    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("1024011KB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("1024011KB");
        return factory.createMultipartConfig();
    }
}
