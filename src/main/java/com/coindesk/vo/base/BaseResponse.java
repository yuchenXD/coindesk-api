package com.coindesk.vo.base;

import com.coindesk.constant.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @category vo
 * @author yuchen liu
 * @description base response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    public BaseResponse(Header header) {
        this.header = header;
    }

    public BaseResponse(StatusCode statusCode) {
        this.header = new Header(statusCode);
    }

    private Header header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;

    @Data
    @AllArgsConstructor
    public static class Header {
        public Header(StatusCode statusCode) {
            this.code = statusCode.getCode();
            this.message = statusCode.getMessage();
        }
        // 代碼
        private String code;
        // 訊息
        private String message;

    }

}
