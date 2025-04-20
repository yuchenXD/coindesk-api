package com.coindesk.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @category Entity
 * @author yuchen liu
 * @description 幣別
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "currency")
public class CurrencyDao {

    /**
     * 主鍵 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 幣別代碼
     */
    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    /**
     * 中文名稱
     */
    @Column(name = "chinese_name", nullable = false, length = 50)
    private String chineseName;

}
