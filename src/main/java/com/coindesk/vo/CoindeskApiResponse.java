package com.coindesk.vo;

import java.util.Map;

import com.coindesk.dto.CoindeskApiDto.Bpi;
import com.coindesk.dto.CoindeskApiDto.DataTime;

import lombok.Data;

/**
 * @category vo
 * @author yuchen liu
 * @description /coindesk/api/v1 的 response
 */
@Data
public class CoindeskApiResponse {
    // 更新時間
    private DataTime time;
    // 免責聲明
    private String disclaimer;
    // 圖表名稱
    private String chartName;
    // 貨幣資料
    private Map<String, Bpi> bpi;
}
