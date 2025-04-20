package com.coindesk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coindesk.dao.CurrencyDao;

/**
 * @category Repository
 * @author yuchen liu
 * @description 幣別 資料庫存取層
 */
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyDao, Long> {

    /**
     * 根據幣別代碼查詢幣別
     * 
     * @param code 幣別代碼
     * @return Optional<CurrencyDao>
     */
    Optional<CurrencyDao> findByCode(String code);

}
