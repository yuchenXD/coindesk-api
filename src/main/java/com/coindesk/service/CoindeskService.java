package com.coindesk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

/**
 * @category Service
 * @author yuchen liu
 * @description Coindesk API 業務邏輯層
 */
@Service
@RequiredArgsConstructor
public class CoindeskService {

    private final RestTemplate restTemplate;

    @Value("${app.rest-template.coindesk.url}")
    private String coindeskUrl;

    /**
     * 取得 Coindesk API 資料
     */
    public void getCoindeskApi() {
        String response = restTemplate.getForObject(coindeskUrl, String.class);




        // TODO 實作 Coindesk API 的業務邏輯
    }

}
