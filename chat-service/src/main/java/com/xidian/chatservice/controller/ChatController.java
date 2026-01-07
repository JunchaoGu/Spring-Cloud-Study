package com.xidian.chatservice.controller;

import com.xidian.chatservice.common.Result;
import com.xidian.chatservice.model.ChatRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 聊天控制器
 * 作用：接收用户请求，发送到 Kafka
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    // Kafka 发送工具（Spring 自动注入）
    private final KafkaTemplate<String, ChatRequest> kafkaTemplate;

    // Kafka Topic 名称
    private static final String TOPIC = "chat-request";

    // 构造函数注入（推荐方式）
    public ChatController(KafkaTemplate<String, ChatRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 提交聊天请求
     *
     * 接口地址：POST /api/chat/submit
     * 参数：userId=xxx&question=xxx
     *
     * @param userId 用户ID
     * @param question 问题
     * @param sessionId 会话ID（可选）
     * @return 返回统一的Result对象，包含requestId等信息
     */
    @PostMapping("/submit")
    public Result<Map<String, Object>> submitChat(
            @RequestParam String userId,
            @RequestParam String question,
            @RequestParam(required = false) String sessionId) {

        // 1. 生成唯一请求ID（UUID）
        String requestId = UUID.randomUUID().toString();

        // 2. 构建 Kafka 消息
        ChatRequest request = new ChatRequest();
        request.setRequestId(requestId);
        request.setUserId(userId);
        request.setQuestion(question);
        request.setSessionId(sessionId);
        request.setTimestamp(System.currentTimeMillis());
        request.setPriority(1);  // 默认优先级

        // 3. 发送到 Kafka（异步，立即返回）
        kafkaTemplate.send(TOPIC, requestId, request);

        log.info(" 请求已入队: requestId={}, userId={}, question={}",
                requestId, userId, question);

        // 4. 构建返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("requestId", requestId);
        data.put("status", "queued");

        // 5. 返回统一的Result对象
        return Result.success(data, "请求已提交，请使用 requestId 建立 SSE 连接");
    }

    /**
     * 测试接口（可选）
     *
     * 接口地址：GET /api/chat/test
     */
    @GetMapping("/test")
    public Result<String> test() {
        return Result.success("Chat Service is running! ");
    }
}