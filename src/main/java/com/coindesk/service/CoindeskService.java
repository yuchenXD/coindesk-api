package com.coindesk.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coindesk.dto.CoindeskApiDto;
import com.coindesk.vo.CoindeskApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @category Service
 * @author yuchen liu
 * @description Coindesk API 業務邏輯層
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CoindeskService {

    private final RestTemplate restTemplate;

    @Value("${app.rest-template.coindesk.url}")
    private String coindeskUrl;

    /**
     * 取得 Coindesk API 資料
     */
    public CoindeskApiResponse getCoindeskApi() {
        // 初始化
        CoindeskApiResponse response = new CoindeskApiResponse();
        // 取得 Coindesk API 資料
        CoindeskApiDto dto = restTemplate.getForObject(coindeskUrl, CoindeskApiDto.class);
        // 設定 response
        BeanUtils.copyProperties(dto, response);
        return response;
    }

}
