package com.coindesk.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @category Aspect
 * @author AI Assistant
 * @description 日誌切面，用於記錄方法執行時間和參數
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAspect {

    private final static String LINE = "-----------------------------------------------------------";

    private final HttpServletRequest httpServletRequest;

    /**
     * 定義切入點：所有 controller 包下的所有方法
     */
    @Pointcut("execution(* com.coindesk.controller..*.*(..))")
    public void controllerPointcut() {
    }

    /**
     * 在方法執行前記錄參數
     * 
     * @param joinPoint 連接點
     */
    @Before("controllerPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        // log
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        // http
        String method = httpServletRequest.getMethod();
        String url = httpServletRequest.getRequestURL().toString();
        String contentType = httpServletRequest.getContentType();
        // controller
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        // log
        appendLine(sb, LINE);
        appendLine(sb, "  http");
        appendLine(sb, "    method       : {}");
        appendLine(sb, "    url          : {}");
        appendLine(sb, "    content-type : {}");
        appendLine(sb, "  controller");
        appendLine(sb, "    class        : {}() { }");
        appendLine(sb, "    method       : {}()");
        appendLine(sb, "  params");
        appendLine(sb, "    args:        : {}");
        appendLine(sb, LINE);
        log.info(sb.toString(), method, url, contentType, className, methodName, args);
    }

    /**
     * 在方法執行後記錄返回值
     * 
     * @param joinPoint 連接點
     * @param result 返回值
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // log
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        // http
        int status = 0;
        if (result instanceof ResponseEntity<?>) {
            status = ((ResponseEntity<?>) result).getStatusCodeValue();
        } 
        // controller
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        // log
        appendLine(sb, LINE);
        // http
        appendLine(sb, "  http");
        appendLine(sb, "    status       : {}");
        appendLine(sb, "  controller");
        appendLine(sb, "    class        : {}() { }");
        appendLine(sb, "    method       : {}()");
        appendLine(sb, "  return");
        appendLine(sb, "    result       : {}");
        appendLine(sb, LINE);
        log.info(sb.toString(),status, className, methodName, result);
    }

    /**
     * StringBuilder 自動換行
     * 
     * @param sb StringBuilder
     * @param text 訊息
     */
    private void appendLine(StringBuilder sb, String text) {
        sb.append(text).append("\n");
    }
}
