package com.xidian.chatservice.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 作用：统一处理 Controller 中抛出的异常，返回统一的 Result 格式
 * 
 * 注意：SSE 流式响应（SseEmitter）的异常需要特殊处理，不能使用此处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 RuntimeException 异常
     * 
     * @param e 运行时异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> handleRuntimeException(RuntimeException e) {
        log.error("❌ 运行时异常: {}", e.getMessage(), e);
        
        // 根据异常消息判断使用哪个状态码
        String message = e.getMessage();
        if (message != null && message.contains("连接已存在")) {
            return Result.error(ResultCode.CONFLICT, message);
        }
        
        return Result.error(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 处理 Exception 异常（兜底处理）
     * 
     * @param e 异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        log.error("❌ 系统异常: {}", e.getMessage(), e);
        return Result.error(ResultCode.INTERNAL_SERVER_ERROR, "系统异常，请联系管理员");
    }

    /**
     * 处理 IllegalArgumentException 异常（参数异常）
     * 
     * @param e 参数异常
     * @return 统一的错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("⚠️ 参数异常: {}", e.getMessage());
        return Result.error(ResultCode.BAD_REQUEST, "参数错误: " + e.getMessage());
    }
}

