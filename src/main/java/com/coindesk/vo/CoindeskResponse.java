package com.coindesk.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @category vo
 * @author yuchen liu
 * @description /coindesk/api/v2 的 response
 */
@Data
public class CoindeskResponse {
    // 更新時間
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonProperty("update_time")
    private LocalDateTime updateTime;
    // 幣別資訊
    @JsonProperty("currency_info")
    private List<Object> currencyInfo;
}
