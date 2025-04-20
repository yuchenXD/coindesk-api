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
 * @author AI Assistant
 * @description 全局異常處理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 處理實體未找到異常
     * 
     * @param e 異常
     * @return ResponseEntity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<>(StatusCode.DATA_NOT_FOUND));
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
        StringBuilder errorMsg = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errorMsg.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(StatusCode.INVALID_REQUEST));
    }

    /**
     * 處理非法參數異常
     * 
     * @param e 異常
     * @return ResponseEntity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(StatusCode.INVALID_REQUEST));
    }

    /**
     * 處理其他異常
     * 
     * @param e 異常
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleException(Exception e) {
        log.error("系統異常: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse<>(StatusCode.FAILURE));
    }
}
