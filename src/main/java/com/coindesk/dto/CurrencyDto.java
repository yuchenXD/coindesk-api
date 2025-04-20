package com.coindesk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @category DTO
 * @author yuchen liu
 * @description 幣別資料傳輸物件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {

    /**
     * 幣別代碼
     */
    private String code;

    /**
     * 中文名稱
     */
    private String chineseName;
}
