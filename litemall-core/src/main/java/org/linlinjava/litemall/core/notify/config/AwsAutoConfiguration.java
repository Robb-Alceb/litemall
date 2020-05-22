package org.linlinjava.litemall.core.notify.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.pinpoint.AmazonPinpointClientBuilder;
import org.linlinjava.litemall.core.notify.AwsNotifyService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：stephen
 * @date ：Created in 2020/5/21 15:59
 * @description：TODO
 */

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class AwsAutoConfiguration {
    private final AwsProperties properties;

    public AwsAutoConfiguration(AwsProperties properties){
        this.properties = properties;
    }

    @Bean
    public AwsNotifyService awsNotifyService(){
        AwsNotifyService notifyService = new AwsNotifyService();
        if(properties.getEnable()){
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
            notifyService.setProperties(properties);
            notifyService.setClient(AmazonPinpointClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(properties.getRegion()).build());
        }
        return notifyService;
    }
}
