package com.coindesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.coindesk.dao.CurrencyDao;
import com.coindesk.dto.CoindeskApiDto;
import com.coindesk.repository.CurrencyRepository;
import com.coindesk.vo.CoindeskApiResponse;
import com.coindesk.vo.CoindeskResponse;

/**
 * @category Test
 * @author yuchen liu
 * @description Coindesk API 業務邏輯層測試
 */
@ExtendWith(MockitoExtension.class)
public class CoindeskServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CoindeskService coindeskService;

    private CoindeskApiDto apiDto;
    private CurrencyDao usdDao;
    private CurrencyDao eurDao;
    private CurrencyDao gbpDao;

    @BeforeEach
    public void setup() {
        // 設置 coindeskUrl
        ReflectionTestUtils.setField(coindeskService, "coindeskUrl", "https://api.coindesk.com/v1/bpi/currentprice.json");

        // 初始化 CoindeskApiDto
        apiDto = new CoindeskApiDto();
        
        // 設置時間
        CoindeskApiDto.DataTime time = new CoindeskApiDto.DataTime();
        time.setUpdated("Jul 27, 2023 12:34:56 UTC");
        time.setUpdatedISO("2023-07-27T12:34:56+00:00");
        time.setUpdateduk("Jul 27, 2023 at 13:34 BST");
        apiDto.setTime(time);
        
        // 設置其他屬性
        apiDto.setDisclaimer("Unit Test");
        apiDto.setChartName("Bitcoin");
        
        // 設置幣別資料
        Map<String, CoindeskApiDto.Bpi> bpi = new LinkedHashMap<>();
        
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
        
        apiDto.setBpi(bpi);

        // 初始化 CurrencyDao
        usdDao = new CurrencyDao();
        usdDao.setId(1L);
        usdDao.setCode("USD");
        usdDao.setChineseName("美元");

        eurDao = new CurrencyDao();
        eurDao.setId(2L);
        eurDao.setCode("EUR");
        eurDao.setChineseName("歐元");

        gbpDao = new CurrencyDao();
        gbpDao.setId(3L);
        gbpDao.setCode("GBP");
        gbpDao.setChineseName("英鎊");
    }

    @Test
    @DisplayName("測試取得原始 Coindesk API 資料")
    public void testGetCoindeskApi() {
        // 模擬 RestTemplate 行為
        when(restTemplate.getForObject(anyString(), eq(CoindeskApiDto.class))).thenReturn(apiDto);

        // 執行測試
        CoindeskApiResponse response = coindeskService.getCoindeskApi();

        // 驗證結果
        assertNotNull(response);
        assertEquals("Bitcoin", response.getChartName());
        assertEquals("Unit Test", response.getDisclaimer());
        assertEquals(3, response.getBpi().size());
        assertEquals("USD", response.getBpi().get("USD").getCode());
        assertEquals("GBP", response.getBpi().get("GBP").getCode());
        assertEquals("EUR", response.getBpi().get("EUR").getCode());
    }

    @Test
    @DisplayName("測試取得轉換後的 Coindesk API 資料")
    public void testGetCoindesk() {
        // 模擬 RestTemplate 行為
        when(restTemplate.getForObject(anyString(), eq(CoindeskApiDto.class))).thenReturn(apiDto);
        
        // 模擬 Repository 行為
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.of(usdDao));
        when(currencyRepository.findByCode("EUR")).thenReturn(Optional.of(eurDao));
        when(currencyRepository.findByCode("GBP")).thenReturn(Optional.of(gbpDao));

        // 執行測試
        CoindeskResponse response = coindeskService.getCoindesk();

        // 驗證結果
        assertNotNull(response);
        assertNotNull(response.getUpdateTime());
        assertEquals(3, response.getInfos().size());
        
        // 驗證幣別資訊
        assertEquals("USD", response.getInfos().get(0).getCode());
        assertEquals("美元", response.getInfos().get(0).getName());
        assertEquals("&#36;", response.getInfos().get(0).getSymbol());
        assertEquals(29608.3217, response.getInfos().get(0).getRate());
    }

    @Test
    @DisplayName("測試取得轉換後的 Coindesk API 資料 - 找不到幣別")
    public void testGetCoindeskCurrencyNotFound() {
        // 模擬 RestTemplate 行為
        when(restTemplate.getForObject(anyString(), eq(CoindeskApiDto.class))).thenReturn(apiDto);
        
        // 模擬 Repository 行為 - 找不到幣別
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.empty());

        // 執行測試並驗證結果
        assertThrows(IllegalArgumentException.class, () -> {
            coindeskService.getCoindesk();
        });
    }
}
