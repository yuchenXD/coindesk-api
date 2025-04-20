package com.coindesk.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coindesk.constant.StatusCode;
import com.coindesk.controller.base.BaseController;
import com.coindesk.service.CurrencyService;
import com.coindesk.vo.CurrencyCreateRequest;
import com.coindesk.vo.CurrencyQueryResponse;
import com.coindesk.vo.CurrencyUpdateRequest;
import com.coindesk.vo.base.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @category Controller
 * @author yuchen liu
 * @description 幣別 API 接口層
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
@Tag(name = "Currency API", description = "幣別管理 API")
public class CurrencyController extends BaseController {

    private final CurrencyService currencyService;

    /**
     * 查詢幣別
     * 
     * @param code 幣別代碼
     */
    @GetMapping("/query")
    @Operation(summary = "查詢幣別", description = "取得所有幣別資料")
    public ResponseEntity<BaseResponse<CurrencyQueryResponse>> query(@RequestParam(required = false) String code) {
        CurrencyQueryResponse response;
        if (StringUtils.isBlank(code)) {
            // 查詢所有幣別
            response = currencyService.findAll();
        } else {
            // 查詢單一幣別
            response = currencyService.query(code);
        }
        return ok(response, StatusCode.READ_SUCCESS);
    }

    /**
     * 新增幣別
     * 
     * @param request 幣別資料
     */
    @PostMapping("/create")
    @Operation(summary = "新增幣別", description = "新增幣別資料")
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody @Valid CurrencyCreateRequest request) {
        currencyService.create(request);
        return ok(StatusCode.CREATE_SUCCESS);
    }

    /**
     * 更新幣別
     * 
     * @param request 幣別資料
     */
    @PatchMapping("/update")
    @Operation(summary = "更新幣別", description = "更新幣別資料")
    public ResponseEntity<BaseResponse<Void>> update(@RequestBody @Valid CurrencyUpdateRequest request) {
        currencyService.update(request);
        return ok(StatusCode.UPDATE_SUCCESS);
    }

    /**
     * 刪除幣別
     * 
     * @param code 幣別代碼
     */
    @DeleteMapping("/delete")
    @Operation(summary = "刪除幣別", description = "刪除幣別資料")
    public ResponseEntity<BaseResponse<Void>> delete(@RequestParam(required = true) String code) {
        currencyService.delete(code);
        return ok(StatusCode.DELETE_SUCCESS);
    }

}
