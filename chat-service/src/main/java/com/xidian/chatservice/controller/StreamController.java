package com.xidian.chatservice.controller;

import com.xidian.chatservice.common.Result;
import com.xidian.chatservice.manager.SseEmitterManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

/**
 * SSE 流式控制器
 * 作用：建立 SSE 连接，供前端接收流式数据
 */
@Slf4j
@RestController
@RequestMapping("/api/stream")
public class StreamController {

    private final SseEmitterManager sseEmitterManager;

    public StreamController(SseEmitterManager sseEmitterManager) {
        this.sseEmitterManager = sseEmitterManager;
    }

    /**
     * 建立 SSE 连接
     *
     * 接口地址：GET /api/stream/{requestId}
     *
     * @param requestId 请求ID（从 /api/chat/submit 获得）
     * @return SseEmitter 对象
     */
    @GetMapping("/{requestId}")                    //订阅地址；
    public SseEmitter stream(@PathVariable String requestId) {
        log.info("🔗 建立 SSE 连接: {}", requestId);

        // 检查是否已存在连接（避免重复连接）
        if (sseEmitterManager.exists(requestId)) {
            log.warn("⚠️ SSE 连接已存在: {}", requestId);
            // 注意：SSE连接异常会由全局异常处理器捕获，返回Result格式
            // 但SSE连接本身不能返回Result，所以这里抛出异常会被Spring处理
            throw new RuntimeException("连接已存在，请勿重复连接");
        }

        // 创建 SSE 连接
        return sseEmitterManager.createEmitter(requestId);
    }

    /**
     * 查询连接状态（可选，用于调试）
     *
     * 接口地址：GET /api/stream/status/{requestId}
     * 
     * @param requestId 请求ID
     * @return 返回统一的Result对象，包含连接状态信息
     */
    @GetMapping("/status/{requestId}")
    public Result<Map<String, Object>> getStatus(@PathVariable String requestId) {
        Map<String, Object> status = new HashMap<>();
        status.put("requestId", requestId);
        status.put("connected", sseEmitterManager.exists(requestId));
        status.put("totalConnections", sseEmitterManager.getConnectionCount());
        return Result.success(status);
    }
}