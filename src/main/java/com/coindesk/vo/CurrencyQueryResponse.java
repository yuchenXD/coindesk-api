package com.coindesk.vo;

import java.util.List;

import com.coindesk.dto.CurrencyDto;

import lombok.Data;

/**
 * @category vo
 * @author yuchen liu
 * @description /currency/query 的 response
 */
@Data
public class CurrencyQueryResponse {
    // 幣別資料
    List<CurrencyDto> currencies;
}
