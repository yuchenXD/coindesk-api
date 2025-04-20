package com.coindesk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coindesk.dao.CurrencyDao;
import com.coindesk.dto.CurrencyDto;
import com.coindesk.repository.CurrencyRepository;
import com.coindesk.vo.CurrencyCreateRequest;
import com.coindesk.vo.CurrencyQueryResponse;
import com.coindesk.vo.CurrencyUpdateRequest;

/**
 * @category Test
 * @author yuchen liu
 * @description 幣別 業務邏輯層測試
 */
@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    private CurrencyDao usdDao;
    private CurrencyDao eurDao;
    private CurrencyCreateRequest createRequest;
    private CurrencyUpdateRequest updateRequest;

    @BeforeEach
    public void setup() {
        // 初始化測試資料
        usdDao = new CurrencyDao();
        usdDao.setId(1L);
        usdDao.setCode("USD");
        usdDao.setChineseName("美元");

        eurDao = new CurrencyDao();
        eurDao.setId(2L);
        eurDao.setCode("EUR");
        eurDao.setChineseName("歐元");

        createRequest = new CurrencyCreateRequest();
        createRequest.setCode("JPY");
        createRequest.setChineseName("日圓");

        updateRequest = new CurrencyUpdateRequest();
        updateRequest.setCode("USD");
        updateRequest.setChineseName("美金");
    }

    @Test
    @DisplayName("測試查詢所有幣別")
    public void testFindAll() {
        // 準備測試資料
        List<CurrencyDao> daos = new ArrayList<>();
        daos.add(usdDao);
        daos.add(eurDao);

        // 模擬 Repository 行為
        when(currencyRepository.findAll()).thenReturn(daos);

        // 執行測試
        CurrencyQueryResponse response = currencyService.findAll();

        // 驗證結果
        assertNotNull(response);
        assertEquals(2, response.getCurrencies().size());
        assertEquals("USD", response.getCurrencies().get(0).getCode());
        assertEquals("美元", response.getCurrencies().get(0).getChineseName());
        assertEquals("EUR", response.getCurrencies().get(1).getCode());
        assertEquals("歐元", response.getCurrencies().get(1).getChineseName());
    }

    @Test
    @DisplayName("測試查詢單一幣別")
    public void testQuery() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.of(usdDao));

        // 執行測試
        CurrencyQueryResponse response = currencyService.query("USD");

        // 驗證結果
        assertNotNull(response);
        assertEquals(1, response.getCurrencies().size());
        assertEquals("USD", response.getCurrencies().get(0).getCode());
        assertEquals("美元", response.getCurrencies().get(0).getChineseName());
    }

    @Test
    @DisplayName("測試查詢不存在的幣別")
    public void testQueryNotFound() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.empty());

        // 執行測試並驗證結果
        assertThrows(EntityNotFoundException.class, () -> {
            currencyService.query("XXX");
        });
    }

    @Test
    @DisplayName("測試新增幣別")
    public void testCreate() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.empty());
        when(currencyRepository.save(any(CurrencyDao.class))).thenReturn(new CurrencyDao());

        // 執行測試
        currencyService.create(createRequest);

        // 驗證 Repository 方法被調用
        verify(currencyRepository, times(1)).findByCode(anyString());
        verify(currencyRepository, times(1)).save(any(CurrencyDao.class));
    }

    @Test
    @DisplayName("測試新增已存在的幣別")
    public void testCreateDuplicate() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.of(usdDao));

        // 執行測試並驗證結果
        assertThrows(IllegalArgumentException.class, () -> {
            currencyService.create(createRequest);
        });
    }

    @Test
    @DisplayName("測試更新幣別")
    public void testUpdate() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.of(usdDao));
        when(currencyRepository.save(any(CurrencyDao.class))).thenReturn(usdDao);

        // 執行測試
        currencyService.update(updateRequest);

        // 驗證 Repository 方法被調用
        verify(currencyRepository, times(1)).findByCode(anyString());
        verify(currencyRepository, times(1)).save(any(CurrencyDao.class));
    }

    @Test
    @DisplayName("測試更新不存在的幣別")
    public void testUpdateNotFound() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.empty());

        // 執行測試並驗證結果
        assertThrows(EntityNotFoundException.class, () -> {
            currencyService.update(updateRequest);
        });
    }

    @Test
    @DisplayName("測試刪除幣別")
    public void testDelete() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.of(usdDao));
        doNothing().when(currencyRepository).delete(any(CurrencyDao.class));

        // 執行測試
        currencyService.delete("USD");

        // 驗證 Repository 方法被調用
        verify(currencyRepository, times(1)).findByCode(anyString());
        verify(currencyRepository, times(1)).delete(any(CurrencyDao.class));
    }

    @Test
    @DisplayName("測試刪除不存在的幣別")
    public void testDeleteNotFound() {
        // 模擬 Repository 行為
        when(currencyRepository.findByCode(anyString())).thenReturn(Optional.empty());

        // 執行測試並驗證結果
        assertThrows(EntityNotFoundException.class, () -> {
            currencyService.delete("XXX");
        });
    }
}
