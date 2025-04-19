package com.coindesk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coindesk.service.CoindeskService;
import com.coindesk.vo.CoindeskApiResponse;
import com.coindesk.vo.CoindeskResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @category Controller
 * @author yuchen liu
 * @description Coindesk API 接口層
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/coindesk")
@Tag(name = "Coindesk API", description = "取得 Coindesk API 資料")
public class CoindeskController {

    private final CoindeskService coindeskService;

    /**
     * 取得 Coindesk API 資料
     */
    @Tag(name = "Coindesk API v1", description = "取得原 Coindesk API 資料")
    @GetMapping("/api/v1")
    public ResponseEntity<CoindeskApiResponse> getCoindeskApi() {
        CoindeskApiResponse response = coindeskService.getCoindeskApi();
        return ResponseEntity.ok(response);
    }

    /**
     * 取得 Coindesk API 資料 做資料轉換
     */
    @GetMapping("/api/v2")
    public ResponseEntity<CoindeskResponse> getCoindesk() {
        CoindeskResponse response = coindeskService.getCoindesk();
        return ResponseEntity.ok(response);
    }

}
