package com.coindesk.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.coindesk.dto.CoindeskApiDto;
import com.coindesk.service.CoindeskService;
import com.coindesk.vo.CoindeskApiResponse;
import com.coindesk.vo.CoindeskResponse;

/**
 * @category Test
 * @author yuchen liu
 * @description Coindesk API 接口層測試
 */
@WebMvcTest(CoindeskController.class)
public class CoindeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoindeskService coindeskService;

    private CoindeskApiResponse apiResponse;
    private CoindeskResponse coindeskResponse;

    @BeforeEach
    public void setup() {
        // 初始化 CoindeskApiResponse
        apiResponse = new CoindeskApiResponse();
        
        // 設置時間
        CoindeskApiDto.DataTime time = new CoindeskApiDto.DataTime();
        time.setUpdated("Jul 27, 2023 12:34:56 UTC");
        time.setUpdatedISO("2023-07-27T12:34:56+00:00");
        time.setUpdateduk("Jul 27, 2023 at 13:34 BST");
        apiResponse.setTime(time);
        
        // 設置其他屬性
        apiResponse.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index (USD).");
        apiResponse.setChartName("Bitcoin");
        
        // 設置幣別資料
        Map<String, CoindeskApiDto.Bpi> bpi = new HashMap<>();
        
        CoindeskApiDto.Bpi usd = new CoindeskApiDto.Bpi();
        usd.setCode("USD");
        usd.setSymbol("&#36;");
        usd.setRate("29,608.3217");
        usd.setDescription("United States Dollar");
        usd.setRateFloat(29608.3217);
        bpi.put("USD", usd);
        
        CoindeskApiDto.Bpi gbp = new CoindeskApiDto.Bpi();
        gbp.setCode("GBP");
        gbp.setSymbol("&pound;");
        gbp.setRate("23,087.7275");
        gbp.setDescription("British Pound Sterling");
        gbp.setRateFloat(23087.7275);
        bpi.put("GBP", gbp);
        
        CoindeskApiDto.Bpi eur = new CoindeskApiDto.Bpi();
        eur.setCode("EUR");
        eur.setSymbol("&euro;");
        eur.setRate("27,089.6050");
        eur.setDescription("Euro");
        eur.setRateFloat(27089.605);
        bpi.put("EUR", eur);
        
        apiResponse.setBpi(bpi);

        // 初始化 CoindeskResponse
        coindeskResponse = new CoindeskResponse();
        
        // 設置時間
        LocalDateTime now = LocalDateTime.now();
        CoindeskResponse.DataTime updateTime = new CoindeskResponse.DataTime(now, now, now);
        coindeskResponse.setUpdateTime(updateTime);
        
        // 設置幣別資訊
        List<CoindeskResponse.CurrencyInfo> infos = new ArrayList<>();
        
        CoindeskResponse.CurrencyInfo usdInfo = new CoindeskResponse.CurrencyInfo();
        usdInfo.setCode("USD");
        usdInfo.setName("美元");
        usdInfo.setSymbol("&#36;");
        usdInfo.setRate(29608.3217);
        infos.add(usdInfo);
        
        CoindeskResponse.CurrencyInfo gbpInfo = new CoindeskResponse.CurrencyInfo();
        gbpInfo.setCode("GBP");
        gbpInfo.setName("英鎊");
        gbpInfo.setSymbol("&pound;");
        gbpInfo.setRate(23087.7275);
        infos.add(gbpInfo);
        
        CoindeskResponse.CurrencyInfo eurInfo = new CoindeskResponse.CurrencyInfo();
        eurInfo.setCode("EUR");
        eurInfo.setName("歐元");
        eurInfo.setSymbol("&euro;");
        eurInfo.setRate(27089.605);
        infos.add(eurInfo);
        
        coindeskResponse.setInfos(infos);
    }

    @Test
    @DisplayName("測試取得原始 Coindesk API 資料")
    public void testGetCoindeskApi() throws Exception {
        // 模擬 Service 回傳
        when(coindeskService.getCoindeskApi()).thenReturn(apiResponse);

        // 執行測試
        mockMvc.perform(get("/coindesk/api/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value("0000"))
                .andExpect(jsonPath("$.body.chartName").value("Bitcoin"))
                .andExpect(jsonPath("$.body.bpi.USD.code").value("USD"))
                .andExpect(jsonPath("$.body.bpi.GBP.code").value("GBP"))
                .andExpect(jsonPath("$.body.bpi.EUR.code").value("EUR"));
    }

    @Test
    @DisplayName("測試取得轉換後的 Coindesk API 資料")
    public void testGetCoindesk() throws Exception {
        // 模擬 Service 回傳
        when(coindeskService.getCoindesk()).thenReturn(coindeskResponse);

        // 執行測試
        mockMvc.perform(get("/coindesk/api/v2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value("0000"))
                .andExpect(jsonPath("$.body.currency_info[0].code").value("USD"))
                .andExpect(jsonPath("$.body.currency_info[0].name").value("美元"))
                .andExpect(jsonPath("$.body.currency_info[1].code").value("GBP"))
                .andExpect(jsonPath("$.body.currency_info[1].name").value("英鎊"))
                .andExpect(jsonPath("$.body.currency_info[2].code").value("EUR"))
                .andExpect(jsonPath("$.body.currency_info[2].name").value("歐元"));
    }
}
