package com.coindesk.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coindesk.constant.StatusCode;
import com.coindesk.vo.base.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @category Exception
 * @author Yuchen Liu
 * @description 全局異常處理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static String LINE = "-----------------------------------------------------------";

    /**
     * 處理實體未找到異常
     *
     * @param e 異常
     * @return ResponseEntity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleEntityNotFoundException(EntityNotFoundException e) {
        // 創建響應
        ResponseEntity<BaseResponse<String>> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<>(StatusCode.DATA_NOT_FOUND));

        // 記錄日誌
        logger(e, response);

        return response;
    }

    /**
     * 處理參數驗證異常
     *
     * @param e 異常
     * @return ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        // 構建錯誤消息
        StringBuilder errorMsg = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errorMsg.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }

        // 創建響應
        ResponseEntity<BaseResponse<String>> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(StatusCode.INVALID_REQUEST));

        // 記錄日誌
        logger(e, response);

        return response;
    }

    /**
     * 處理非法參數異常
     *
     * @param e 異常
     * @return ResponseEntity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        // 創建響應
        ResponseEntity<BaseResponse<String>> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(StatusCode.DUPLICATE_DATA));

        // 記錄日誌
        logger(e, response);

        return response;
    }

    /**
     * 處理其他異常
     *
     * @param e 異常
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleException(Exception e) {
        // 創建響應
        ResponseEntity<BaseResponse<String>> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse<>(StatusCode.FAILURE));

        // 記錄日誌
        logger(e, response);

        return response;
    }

    private void logger(Exception e, ResponseEntity<BaseResponse<String>> response) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        appendLine(sb, LINE);
        appendLine(sb, "  exception");
        appendLine(sb, "    type         : {}");
        appendLine(sb, "    message      : {}");
        appendLine(sb, "  http");
        appendLine(sb, "    status       : {}");
        appendLine(sb, "  response");
        appendLine(sb, "    body         : {}");
        appendLine(sb, LINE);
        log.error(sb.toString(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                response.getStatusCodeValue(),
                response.getBody());
    }

    /**
     * StringBuilder 自動換行
     *
     * @param sb   StringBuilder
     * @param text 訊息
     */
    private void appendLine(StringBuilder sb, String text) {
        sb.append(text).append("\n");
    }
}
