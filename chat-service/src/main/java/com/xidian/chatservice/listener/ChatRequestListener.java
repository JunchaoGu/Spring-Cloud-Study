package com.xidian.chatservice.listener;

import com.google.common.util.concurrent.RateLimiter;
import com.xidian.chatservice.manager.SseEmitterManager;
import com.xidian.chatservice.model.ChatRequest;
import com.xidian.chatservice.model.ChatResponse;
import com.xidian.chatservice.service.LLMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka 消费者
 * 作用：从 Kafka 中取出请求，调用大模型，推送结果
 */
@Slf4j
@Component
public class ChatRequestListener {

    private final SseEmitterManager sseEmitterManager;
    private final LLMService llmService;

    // 限流器：每秒最多 20 个请求（通义千问限制）
    private final RateLimiter rateLimiter = RateLimiter.create(20.0);

    public ChatRequestListener(SseEmitterManager sseEmitterManager,
                               LLMService llmService) {
        this.sseEmitterManager = sseEmitterManager;
        this.llmService = llmService;
    }

    /**
     * 监听 Kafka 消息
     *
     * topics: 监听的 Topic 名称
     * groupId: 消费者组ID
     * concurrency: 并发线程数（5个线程同时消费）
     */
    @KafkaListener(
            topics = "chat-request",
            groupId = "chat-consumer-group",
            concurrency = "5"
    )
    public void handleChatRequest(ChatRequest request) {
        String requestId = request.getRequestId();
        log.info("🎯 开始处理请求: {}, question: {}", requestId, request.getQuestion());

        try {
            // 1. 限流控制（阻塞直到获取许可）
            rateLimiter.acquire();
            log.info("🚦 获取限流许可: {}", requestId);

            // 2. 等待 SSE 连接建立（最多等待 10 秒）
            int waitCount = 0;
            while (!sseEmitterManager.exists(requestId) && waitCount < 100) {
                Thread.sleep(100);  // 每次等待 100ms
                waitCount++;
            }

            // 如果 10 秒后还没建立连接，放弃处理
            if (!sseEmitterManager.exists(requestId)) {
                log.warn("⚠️ SSE 连接未建立，放弃处理: {}", requestId);
                return;
            }

            log.info("✅ SSE 连接已建立，开始调用大模型: {}", requestId);

            // 3. 调用大模型 API（流式响应）
            llmService.streamChat(request, (chunk) -> {
                // 每收到一个片段，就推送给前端
                ChatResponse response = ChatResponse.chunk(requestId, chunk);
                sseEmitterManager.sendMessage(requestId, response);
            });

            // 4. 完成响应
            sseEmitterManager.complete(requestId, "");
            log.info("✅ 请求处理完成: {}", requestId);

        } catch (InterruptedException e) {
            log.error("❌ 请求处理被中断: {}", requestId);
            sseEmitterManager.sendError(requestId, "处理被中断");
            Thread.currentThread().interrupt();

        } catch (Exception e) {
            log.error("❌ 请求处理异常: {}, error: {}", requestId, e.getMessage(), e);
            sseEmitterManager.sendError(requestId, "处理异常: " + e.getMessage());
        }
    }
}