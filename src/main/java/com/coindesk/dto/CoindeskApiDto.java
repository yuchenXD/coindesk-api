package com.coindesk.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @category DTO
 * @author yuchen liu
 * @description Coindesk API 資料傳輸物件
 */
@Data
public class CoindeskApiDto {
    // 更新時間
    private DataTime time;
    // 免責聲明
    private String disclaimer;
    // 圖表名稱
    private String chartName;
    // 貨幣資料
    private Map<String, Bpi> bpi;

    @Data
    public static class DataTime {
        // 更新時間
        public String updated;
        // 更新時間 (ISO)
        public String updatedISO;
        // 更新時間 (UK)
        public String updateduk;
    }

    @Data
    public static class Bpi {
        // 貨幣代碼
        public String code;
        // 貨幣符號
        public String symbol;
        // 貨幣名稱
        public String rate;
        // 貨幣名稱 (全名)
        public String description;
        // 貨幣名稱 (全名)
        @JsonProperty("rate_float")
        public Double rateFloat;
    }

}
