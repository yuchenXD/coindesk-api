package com.coindesk.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coindesk.dto.CoindeskApiDto;
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
     * 
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
     * 
     * @return CoindeskResponse
     */
    public CoindeskResponse getCoindesk() {
        // 初始化
        CoindeskResponse response = new CoindeskResponse();
        // 取得 Coindesk API 資料
        CoindeskApiDto dto = getRestData();
        CoindeskApiDto.DataTime time = dto.getTime();
        Map<String, CoindeskApiDto.Bpi> bpiMapping = dto.getBpi();
        // 更新時間
        LocalDateTime updated = getFormatTime("MMM d, yyyy HH:mm:ss z", time.getUpdated());
        // 更新時間 (ISO)
        LocalDateTime updatedISO = LocalDateTime.parse(time.getUpdatedISO().subSequence(0, 19));
        // 更新時間 (UK)
        LocalDateTime updateduk = getFormatTime("MMM d, yyyy 'at' HH:mm z", time.getUpdateduk());
        // 設定 response
        CoindeskResponse.DataTime updateTime = new CoindeskResponse.DataTime(updated, updatedISO, updateduk);
        List<CoindeskResponse.CurrencyInfo> infos = bpiMapping
                .values()
                .stream()
                .map(bpi -> getCurrencyInfo(bpi))
                .collect(Collectors.toList());
        response.setUpdateTime(updateTime);
        response.setInfos(infos);
        return response;
    }

    /**
     * 取得 Coindesk API 資料
     * 
     * @return CoindeskApiDto
     */
    private CoindeskApiDto getRestData() {
        return restTemplate.getForObject(coindeskUrl, CoindeskApiDto.class);
    }

    /**
     * 取得幣別資訊
     * 
     * @param bpi 幣別資料
     * @return CoindeskResponse.CurrencyInfo
     */
    private CoindeskResponse.CurrencyInfo getCurrencyInfo(CoindeskApiDto.Bpi bpi) {
        CoindeskResponse.CurrencyInfo info = new CoindeskResponse.CurrencyInfo();
        info.setCode(bpi.getCode());
        info.setName(bpi.getDescription());
        info.setSymbol(bpi.getSymbol());
        info.setRate(bpi.getRateFloat());
        return info;
    }

    /**
     * 取得格式化時間
     * 
     * @param format 格式化規則
     * @param time   時間
     * @return LocalDateTime
     */
    private LocalDateTime getFormatTime(String format, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
        return LocalDateTime.parse(time, formatter);
    }

}
