package com.coindesk.constant;

/**
 * @category Constant
 * @author yuchen liu
 * @description 交易代碼
 */
public enum StatusCode {

    SUCCESS("0000", "交易成功"),
    CREATE_SUCCESS("0001", "新增成功"),
    READ_SUCCESS("0002", "查詢成功"),
    UPDATE_SUCCESS("0003", "更新成功"),
    DELETE_SUCCESS("0004", "刪除成功"),

    INVALID_REQUEST("9995", "無效的請求"),
    INVALID_PARAMETER("9996", "無效的參數"),
    DATA_NOT_FOUND("9997", "資料不存在"),
    DUPLICATE_DATA("9998", "資料重複"),
    FAILURE("9999", "系統錯誤");
    
    private final String code;
    
    private final String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
