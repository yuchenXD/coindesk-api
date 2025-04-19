package com.coindesk.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @category Config
 * @author yuchen liu
 * @description RestTemplate 配置
 */
@Configuration
public class RestTemplateConfig {

    @Value("${app.rest-template.connect-timeout}")
    private Integer connectTimeout;

    @Value("${app.rest-template.read-timeout}")
    private Integer readTimeout;

    /**
     * 配置 RestTemplate Bean
     *
     * @param builder RestTemplateBuilder
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout)) // 連接時間
                .setReadTimeout(Duration.ofMillis(readTimeout)) // 讀取時間
                .build();
    }
}
