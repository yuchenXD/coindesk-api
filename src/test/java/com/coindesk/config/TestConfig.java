package com.coindesk.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @category Config
 * @author yuchen liu
 * @description 測試配置類
 */
@TestConfiguration
public class TestConfig {

    /**
     * 提供測試用的 RestTemplate
     * 
     * @return RestTemplate
     */
    @Bean
    @Primary
    public RestTemplate testRestTemplate() {
        return new RestTemplate();
    }
}
