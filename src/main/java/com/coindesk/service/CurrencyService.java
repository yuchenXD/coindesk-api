package com.coindesk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.coindesk.dao.CurrencyDao;
import com.coindesk.dto.CurrencyDto;
import com.coindesk.repository.CurrencyRepository;
import com.coindesk.vo.CurrencyCreateRequest;
import com.coindesk.vo.CurrencyQueryResponse;
import com.coindesk.vo.CurrencyUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @category Service
 * @author yuchen liu
 * @description 幣別 業務邏輯層
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    /**
     * 查詢所有幣別
     * 
     * @return CurrencyQueryResponse
     */
    public CurrencyQueryResponse findAll() {
        // 初始化
        CurrencyQueryResponse response = new CurrencyQueryResponse();
        // 查詢
        List<CurrencyDao> currencyDao = currencyRepository.findAll();
        List<CurrencyDto> currencies = currencyDao.stream().map(dao -> {
            CurrencyDto currencyDto = new CurrencyDto();
            BeanUtils.copyProperties(dao, currencyDto);
            return currencyDto;
        }).collect(Collectors.toList());
        // 設定 response
        response.setCurrencies(currencies);
        return response;
    }

    /**
     * 查詢幣別
     * 
     * @param code 幣別代碼
     * @return CurrencyQueryResponse
     */
    public CurrencyQueryResponse query(String code) {
        // 初始化
        CurrencyQueryResponse response = new CurrencyQueryResponse();
        List<CurrencyDto> currencies = new ArrayList<>();
        // 查詢
        Optional<CurrencyDao> currencyOpt = currencyRepository.findByCode(code);
        if (currencyOpt.isPresent()) {
            CurrencyDao currencyDao = currencyOpt.get();
            CurrencyDto currencyDto = new CurrencyDto();
            BeanUtils.copyProperties(currencyDao, currencyDto);
            currencies.add(currencyDto);
            // 設定 response
            response.setCurrencies(currencies);
            return response;
        } else {
            // 查無資料
            throw new EntityNotFoundException("找不到幣別代碼: " + code);
        }
    }

    /**
     * 新增幣別
     * 
     * @param entity
     * @return
     */
    public void create(CurrencyCreateRequest request) {
        // 初始化
        String code = request.getCode();
        // 查詢
        Optional<CurrencyDao> currencyOpt = currencyRepository.findByCode(code);
        // 重複資料
        if (currencyOpt.isPresent()) {
            throw new IllegalArgumentException("幣別代碼已存在: " + code);
        }
        // 建立幣別
        CurrencyDao currencyDao = new CurrencyDao();
        BeanUtils.copyProperties(request, currencyDao);
        currencyRepository.save(currencyDao);
    }

    /**
     * 更新幣別
     * 
     * @param request 更新資料
     */
    public void update(CurrencyUpdateRequest request) {
        // 初始化
        String code = request.getCode();
        String chineseName = request.getChineseName();
        // 查詢幣別
        Optional<CurrencyDao> currencyOpt = currencyRepository.findByCode(code);
        if (!currencyOpt.isPresent()) {
            throw new EntityNotFoundException("找不到幣別代碼: " + code);
        }
        // 更新幣別
        CurrencyDao currency = currencyOpt.get();
        currency.setChineseName(chineseName);
        currencyRepository.save(currency);
    }

    /**
     * 刪除幣別
     * 
     * @param code 幣別代碼
     */
    public void delete(String code) {
        // 查詢幣別
        Optional<CurrencyDao> currencyOpt = currencyRepository.findByCode(code);
        if (!currencyOpt.isPresent()) {
            throw new EntityNotFoundException("找不到幣別代碼: " + code);
        }
        // 刪除幣別
        currencyRepository.delete(currencyOpt.get());
    }
}
