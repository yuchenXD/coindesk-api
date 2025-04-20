package com.coindesk.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.coindesk.constant.StatusCode;
import com.coindesk.dto.CurrencyDto;
import com.coindesk.service.CurrencyService;
import com.coindesk.vo.CurrencyCreateRequest;
import com.coindesk.vo.CurrencyQueryResponse;
import com.coindesk.vo.CurrencyUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @category Test
 * @author yuchen liu
 * @description 幣別 API 接口層測試
 */
@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrencyService currencyService;

    private CurrencyQueryResponse queryResponse;
    private CurrencyCreateRequest createRequest;
    private CurrencyUpdateRequest updateRequest;

    @BeforeEach
    public void setup() {
        // 初始化查詢回應
        queryResponse = new CurrencyQueryResponse();
        List<CurrencyDto> currencies = new ArrayList<>();
        
        CurrencyDto usd = new CurrencyDto();
        usd.setCode("USD");
        usd.setChineseName("美元");
        currencies.add(usd);
        
        CurrencyDto eur = new CurrencyDto();
        eur.setCode("EUR");
        eur.setChineseName("歐元");
        currencies.add(eur);
        
        queryResponse.setCurrencies(currencies);

        // 初始化創建請求
        createRequest = new CurrencyCreateRequest();
        createRequest.setCode("JPY");
        createRequest.setChineseName("日圓");

        // 初始化更新請求
        updateRequest = new CurrencyUpdateRequest();
        updateRequest.setCode("USD");
        updateRequest.setChineseName("美金");
    }

    @Test
    @DisplayName("測試查詢所有幣別")
    public void testQueryAll() throws Exception {
        // 模擬 Service 回傳
        when(currencyService.findAll()).thenReturn(queryResponse);

        // 執行測試
        mockMvc.perform(get("/currency/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(StatusCode.READ_SUCCESS.getCode()))
                .andExpect(jsonPath("$.body.currencies[0].code").value("USD"))
                .andExpect(jsonPath("$.body.currencies[0].chineseName").value("美元"))
                .andExpect(jsonPath("$.body.currencies[1].code").value("EUR"))
                .andExpect(jsonPath("$.body.currencies[1].chineseName").value("歐元"));
    }

    @Test
    @DisplayName("測試查詢單一幣別")
    public void testQueryByCode() throws Exception {
        // 準備單一幣別回應
        CurrencyQueryResponse singleResponse = new CurrencyQueryResponse();
        List<CurrencyDto> currencies = new ArrayList<>();
        CurrencyDto usd = new CurrencyDto();
        usd.setCode("USD");
        usd.setChineseName("美元");
        currencies.add(usd);
        singleResponse.setCurrencies(currencies);

        // 模擬 Service 回傳
        when(currencyService.query(anyString())).thenReturn(singleResponse);

        // 執行測試
        mockMvc.perform(get("/currency/query?code=USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(StatusCode.READ_SUCCESS.getCode()))
                .andExpect(jsonPath("$.body.currencies[0].code").value("USD"))
                .andExpect(jsonPath("$.body.currencies[0].chineseName").value("美元"))
                .andExpect(jsonPath("$.body.currencies.length()").value(1));
    }

    @Test
    @DisplayName("測試新增幣別")
    public void testCreate() throws Exception {
        // 模擬 Service 行為
        doNothing().when(currencyService).create(any(CurrencyCreateRequest.class));

        // 執行測試
        mockMvc.perform(post("/currency/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(StatusCode.CREATE_SUCCESS.getCode()));
    }

    @Test
    @DisplayName("測試更新幣別")
    public void testUpdate() throws Exception {
        // 模擬 Service 行為
        doNothing().when(currencyService).update(any(CurrencyUpdateRequest.class));

        // 執行測試
        mockMvc.perform(patch("/currency/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(StatusCode.UPDATE_SUCCESS.getCode()));
    }

    @Test
    @DisplayName("測試刪除幣別")
    public void testDelete() throws Exception {
        // 模擬 Service 行為
        doNothing().when(currencyService).delete(anyString());

        // 執行測試
        mockMvc.perform(delete("/currency/delete?code=USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(StatusCode.DELETE_SUCCESS.getCode()));
    }
}
