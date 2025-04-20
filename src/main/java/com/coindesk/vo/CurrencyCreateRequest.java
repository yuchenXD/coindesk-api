package com.coindesk.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @category vo
 * @author yuchen liu
 * @description /currency/create 的 request
 */
@Data
public class CurrencyCreateRequest {

    // 幣別代碼
    @NotBlank(message = "幣別代碼不能為空")
    @Size(max = 10, message = "幣別代碼長度不能超過10個字符")
    private String code;

    // 中文名稱
    @JsonProperty("chinese_name")
    @NotBlank(message = "中文名稱不能為空")
    @Size(max = 50, message = "中文名稱長度不能超過50個字符")
    private String chineseName;

}
