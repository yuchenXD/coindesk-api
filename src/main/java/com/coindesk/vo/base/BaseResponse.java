package com.coindesk.vo.base;

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

    public BaseResponse(String header) {
        this.header = header;
    }

    private String header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;

}
