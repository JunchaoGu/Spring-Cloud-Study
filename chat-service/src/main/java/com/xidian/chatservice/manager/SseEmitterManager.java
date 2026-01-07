package com.xidian.chatservice.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xidian.chatservice.model.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SSE 连接管理器
 * 作用：管理所有 SSE 连接，负责创建、推送消息、删除连接
 */
@Slf4j
@Component
public class SseEmitterManager {

    // 存储 requestId 和 SseEmitter 的映射
    // 为什么用 ConcurrentHashMap？因为多个线程会同时访问
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    // 超时时间：5分钟（300秒）
    private static final long TIMEOUT = 5 * 60 * 1000L;

    // JSON 转换工具
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 创建 SSE 连接
     *
     * @param requestId 请求ID
     * @return SseEmitter 对象
     */
    public SseEmitter createEmitter(String requestId) {
        // 创建 SseEmitter 对象，设置超时时间
        SseEmitter emitter = new SseEmitter(TIMEOUT);

        // 注册完成回调（连接正常关闭时触发）
        emitter.onCompletion(() -> {
            log.info("SSE 连接完成: {}", requestId);
            emitterMap.remove(requestId);  // 从 Map 中删除
        });

        // 注册超时回调（超过5分钟未完成时触发）
        emitter.onTimeout(() -> {
            log.warn("SSE 连接超时: {}", requestId);
            emitterMap.remove(requestId);
        });

        // 注册错误回调（连接出错时触发）
        emitter.onError((e) -> {
            log.error("SSE 连接异常: {}, error: {}", requestId, e.getMessage());
            emitterMap.remove(requestId);
        });

        // 存储到 Map 中
        emitterMap.put(requestId, emitter);
        log.info("创建 SSE 连接: {}, 当前连接数: {}", requestId, emitterMap.size());

        return emitter;
    }

    /**
     * 推送消息到前端
     *
     * @param requestId 请求ID
     * @param response 响应对象
     * @return 是否成功
     */
    public boolean sendMessage(String requestId, ChatResponse response) {
        // 从 Map 中获取对应的 SseEmitter
        SseEmitter emitter = emitterMap.get(requestId);

        if (emitter == null) {
            log.warn("SSE 连接不存在: {}", requestId);
            return false;
        }

        try {
            // 发送 JSON 格式的数据
            emitter.send(SseEmitter.event()
                    .name("message")  // 事件名称
                    .data(objectMapper.writeValueAsString(response)));  // JSON 数据

            log.debug("发送消息: {}, content: {}", requestId, response.getContent());
            return true;

        } catch (IOException e) {
            log.error("发送 SSE 消息失败: {}, error: {}", requestId, e.getMessage());
            emitterMap.remove(requestId);  // 发送失败，删除连接
            return false;
        }
    }

    /**
     * 完成 SSE 连接
     *
     * @param requestId 请求ID
     * @param finalMessage 最终消息（可为空）
     */
    public void complete(String requestId, String finalMessage) {
        SseEmitter emitter = emitterMap.get(requestId);

        if (emitter != null) {
            try {
                // 发送结束标志
                ChatResponse response = ChatResponse.end(requestId);
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(objectMapper.writeValueAsString(response)));

                // 标记连接完成
                emitter.complete();
                log.info("SSE 连接完成: {}", requestId);

            } catch (IOException e) {
                log.error("完成 SSE 连接失败: {}", requestId);
            } finally {
                emitterMap.remove(requestId);
            }
        }
    }

    /**
     * 发送错误消息
     *
     * @param requestId 请求ID
     * @param errorMessage 错误信息
     */
    public void sendError(String requestId, String errorMessage) {
        SseEmitter emitter = emitterMap.get(requestId);

        if (emitter != null) {
            try {
                ChatResponse response = ChatResponse.error(requestId, errorMessage);
                emitter.send(SseEmitter.event()
                        .name("error")
                        .data(objectMapper.writeValueAsString(response)));

                // 标记连接异常结束
                emitter.completeWithError(new RuntimeException(errorMessage));

            } catch (IOException e) {
                log.error("发送错误消息失败: {}", requestId);
            } finally {
                emitterMap.remove(requestId);
            }
        }
    }

    /**
     * 检查连接是否存在
     */
    public boolean exists(String requestId) {
        return emitterMap.containsKey(requestId);
    }

    /**
     * 获取当前连接数
     */
    public int getConnectionCount() {
        return emitterMap.size();
    }
}