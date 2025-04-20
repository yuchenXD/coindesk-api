package com.coindesk.controller.base;

import org.springframework.http.ResponseEntity;

import com.coindesk.constant.StatusCode;
import com.coindesk.vo.base.BaseResponse;

public abstract class BaseController {

    public <T> ResponseEntity<BaseResponse<T>> ok(T body) {
        BaseResponse.Header header = new BaseResponse.Header(StatusCode.SUCCESS);
        return ResponseEntity.ok().body(new BaseResponse<>(header, body));
    }

    public <T> ResponseEntity<BaseResponse<T>> ok(T body, StatusCode status) {
        BaseResponse.Header header = new BaseResponse.Header(status);
        return ResponseEntity.ok().body(new BaseResponse<>(header, body));
    }

    public <T> ResponseEntity<BaseResponse<Void>> ok(StatusCode status) {
        BaseResponse.Header header = new BaseResponse.Header(status);
        return ResponseEntity.ok().body(new BaseResponse<>(header));
    }

}
