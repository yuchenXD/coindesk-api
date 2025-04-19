package com.coindesk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coindesk.service.CoindeskService;

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

    @GetMapping("/api")
    public ResponseEntity<Void> getCoindeskApi() {
        coindeskService.getCoindeskApi();
        return ResponseEntity.ok().build();
    }

}
