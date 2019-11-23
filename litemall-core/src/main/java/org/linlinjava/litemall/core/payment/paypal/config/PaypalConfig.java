package org.linlinjava.litemall.core.payment.paypal.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;


/**
 * @author ：stephen
 * @date ：Created in 11/19/2019 2:08 PM
 * @description：TODO
 */

@Configuration
public class PaypalConfig {
    @Value("${litemall.paypal.client.app}")
    private String clientId;
    @Value("${litemall.paypal.client.secret}")
    private String clientSecret;
    @Value("${litemall.paypal.mode}")
    private String mode;

    @Bean
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential(){
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException{
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }

}
