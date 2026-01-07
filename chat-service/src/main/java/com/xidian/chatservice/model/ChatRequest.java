package com.xidian.chatservice.model;

import lombok.Data;
import java.io.Serializable;

/**
 * 聊天请求（Kafka 消息体）
 */
@Data
public class ChatRequest implements Serializable {

    private String requestId;      // 唯一请求ID（UUID）
    private String userId;         // 用户ID
    private String question;       // 用户问题
    private String sessionId;      // 会话ID（可选，用于多轮对话）
    private Long timestamp;        // 请求时间戳
    private Integer priority;      // 优先级（1=普通，2=VIP）       降级策略，我们可以以 0，1，2，3，4，5      基础款，配置不同的策略，隐式降级根据不同维度评估

    // Lombok 的 @Data 会自动生成 getter/setter/toString 等方法
}