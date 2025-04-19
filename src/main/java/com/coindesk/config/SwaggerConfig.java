package com.coindesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @category Config
 * @author yuchen liu
 * @description Swagger 配置
 */
@Configuration
public class SwaggerConfig {

    /**
     * Swagger 配置
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // 主標題
        Info info = new Info()
                .title("Coindesk API")
                .version("1.0.0")
                .description("Coindesk API Documentation");
        return new OpenAPI().info(info);
    }

}
