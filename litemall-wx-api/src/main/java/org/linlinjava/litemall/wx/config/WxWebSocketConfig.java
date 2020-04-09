package org.linlinjava.litemall.wx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author ：stephen
 * @date ：Created in 2020/4/7 18:07
 * @description：TODO
 */
//@Configuration
public class WxWebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter () {
        return new ServerEndpointExporter();
    }
}
