package com.coindesk.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @category vo
 * @author yuchen liu
 * @description /coindesk/api/v2 的 response
 */
@Data
public class CoindeskResponse {
    // 更新時間
    @JsonProperty("update_time")
    private DataTime updateTime;
    // 幣別資訊
    @JsonProperty("currency_info")
    private List<CurrencyInfo> infos;

    @Data
    @AllArgsConstructor
    public static class DataTime {
        // 更新時間
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime updated;
        // 更新時間 (ISO)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime updatedISO;
        // 更新時間 (UK)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime updateduk;
    }

    @Data
    public static class CurrencyInfo {
        // 貨幣代碼
        private String code;
        // 幣別名稱
        private String name;
        // 幣別符號
        private String symbol;
        // 匯率
        private Double rate;
    }
}
