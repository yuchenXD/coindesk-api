package com.coindesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 應用程式的主類
 *
 * 這個類是應用程式的入口點，使用 @SpringBootApplication 註解來啟用 Spring Boot 的自動配置
 */
@SpringBootApplication
public class ApiApplication {

    /**
     * 應用程式的主方法
     *
     * @param args 命令行參數
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
