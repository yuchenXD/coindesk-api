package com.coindesk.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coindesk.dto.CoindeskApiDto;
import com.coindesk.dto.CoindeskApiDto.DataTime;
import com.coindesk.vo.CoindeskApiResponse;
import com.coindesk.vo.CoindeskResponse;

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
     * @return CoindeskApiResponse
     */
    public CoindeskApiResponse getCoindeskApi() {
        // 初始化
        CoindeskApiResponse response = new CoindeskApiResponse();
        // 取得 Coindesk API 資料
        CoindeskApiDto dto = getRestData();
        // 設定 response
        BeanUtils.copyProperties(dto, response);
        return response;
    }

    /**
     * 取得 Coindesk API 資料 做資料轉換
     * @return CoindeskResponse
     */
    public CoindeskResponse getCoindesk() {
        // 初始化
        CoindeskResponse response = new CoindeskResponse();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
        // 取得 Coindesk API 資料
        CoindeskApiDto dto = getRestData();
        // 設定 response
        DataTime time = dto.getTime();
        LocalDateTime updateTime = LocalDateTime.parse(time.getUpdated(), formatter);
        response.setUpdateTime(updateTime);
        response.setCurrencyInfo(new ArrayList<>());
        return response;
    }
    


    /**
     * 取得 Coindesk API 資料
     * @return CoindeskApiDto
     */
    private CoindeskApiDto getRestData() {
        return restTemplate.getForObject(coindeskUrl, CoindeskApiDto.class);
    }

}
