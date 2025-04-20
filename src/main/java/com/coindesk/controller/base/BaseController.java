package com.coindesk.controller.base;

import org.springframework.http.ResponseEntity;

import com.coindesk.vo.base.BaseResponse;

public abstract class BaseController {

    public <T> ResponseEntity<BaseResponse<T>> ok(T body) {
        return ResponseEntity.ok().body(new BaseResponse<>("交易成功", body));
    }

    public <T> ResponseEntity<BaseResponse<Void>> ok() {
        return ResponseEntity.ok().body(new BaseResponse<>("交易成功"));
    }

}
