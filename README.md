# Coindesk API

## 專案概述

這是一個基於 Spring Boot 的 RESTful API 應用程式，用於獲取和管理加密貨幣匯率資訊。專案通過整合 Coindesk API 獲取比特幣對不同法定貨幣的匯率，並提供了幣別管理功能。

## 功能特點

- **Coindesk API 整合**：
  - 獲取原始 Coindesk API 資料
  - 提供資料轉換功能，將原始資料轉換為更易於使用的格式

- **幣別管理**：
  - 查詢所有幣別資訊
  - 查詢特定幣別資訊
  - 新增幣別
  - 更新幣別資訊
  - 刪除幣別

- **其他特點**：
  - RESTful API 設計
  - 全局異常處理
  - 統一的響應格式
  - API 文檔自動生成 (Swagger/OpenAPI)
  - 內置 H2 資料庫

## 技術棧

- **後端框架**：Spring Boot 2.7.18
- **資料庫**：H2 (內存資料庫)
- **API 文檔**：SpringDoc OpenAPI (Swagger)
- **持久層**：Spring Data JPA
- **其他工具**：
  - Lombok - 減少樣板代碼
  - Spring AOP - 面向切面編程
  - Spring Validation - 請求參數驗證

## 安裝與運行

### 前置條件

- JDK 1.8 或更高版本
- Maven 3.6 或更高版本

### 步驟

1. **克隆專案**

```bash
git clone [repository-url]
cd coindesk-api
```
2. **專案初始化**

```bash
mvn clean install
```
3. **編譯專案**

```bash
mvn clean package
```

4. **運行應用程式**

```bash
java -jar target/coindesk-api-0.0.1-SNAPSHOT.jar
```

或使用 Maven 運行：

```bash
mvn spring-boot:run
```

4. **訪問應用程式**

應用程式將在 http://localhost:8080/api 運行

- API 文檔：http://localhost:8080/api/swagger-ui.html
- H2 資料庫控制台：http://localhost:8080/api/h2-console

## API 文檔

### Coindesk API

- `GET /api/coindesk/api/v1` - 獲取原始 Coindesk API 資料
- `GET /api/coindesk/api/v2` - 獲取轉換後的 Coindesk API 資料

### 幣別管理 API

- `GET /api/currency/query` - 查詢所有幣別
- `GET /api/currency/query?code={code}` - 查詢特定幣別
- `POST /api/currency/create` - 新增幣別
- `PATCH /api/currency/update` - 更新幣別
- `DELETE /api/currency/delete?code={code}` - 刪除幣別

詳細的 API 文檔可通過 Swagger UI 查看：http://localhost:8080/api/swagger-ui.html

## 專案結構

```
src/main/java/com/coindesk/
├── ApiApplication.java              # 應用程式入口點
├── config/                          # 配置類
├── constant/                        # 常量定義
├── controller/                      # API 控制器
├── dao/                             # 資料訪問對象
├── dto/                             # 資料傳輸對象
├── exception/                       # 異常處理
├── repository/                      # 資料庫存取層
├── service/                         # 業務邏輯層
└── vo/                              # 值對象 (請求/響應)
```

## 資料庫設計

專案使用 H2 內存資料庫，初始化腳本位於 `src/main/resources/schema.sql`。

主要資料表：
- `currency` - 存儲幣別資訊
  - `id` - 主鍵
  - `code` - 幣別代碼 (如 USD, EUR, GBP)
  - `chinese_name` - 幣別中文名稱

## 開發者資訊

- 作者：Yuchen Liu

## 測試

專案包含單元測試，涵蓋了主要的業務邏輯和 API 端點。可以使用以下命令運行測試：

```bash
mvn test
```

## 授權

本專案僅供學習和參考使用。
