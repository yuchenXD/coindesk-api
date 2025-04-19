-- 建立幣別資料表
CREATE TABLE IF NOT EXISTS currency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    chinese_name VARCHAR(50) NOT NULL,
    CONSTRAINT uk_currency_code UNIQUE (code)
);

-- 初始化幣別資料
INSERT INTO currency (code, chinese_name) VALUES ('USD', '美元');
INSERT INTO currency (code, chinese_name) VALUES ('EUR', '歐元');
INSERT INTO currency (code, chinese_name) VALUES ('GBP', '英鎊');
