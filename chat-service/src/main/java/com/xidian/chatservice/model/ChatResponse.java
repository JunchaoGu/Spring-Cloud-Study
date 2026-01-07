package com.xidian.chatservice.model;

import lombok.Data;

/**
 * 聊天响应（SSE 推送的数据）
 */
@Data
public class ChatResponse {

    private String requestId;      // 请求ID（关联请求）
    private String content;        // 流式内容片段
    private Boolean isEnd;         // 是否结束（true=结束，false=继续）
    private String error;          // 错误信息（有错误时才有值）

    // 简化创建对象的静态方法
    public static ChatResponse chunk(String requestId, String content) {
        ChatResponse response = new ChatResponse();
        response.setRequestId(requestId);
        response.setContent(content);
        response.setIsEnd(false);
        return response;
    }

    public static ChatResponse end(String requestId) {
        ChatResponse response = new ChatResponse();
        response.setRequestId(requestId);
        response.setContent("");
        response.setIsEnd(true);
        return response;
    }

    public static ChatResponse error(String requestId, String error) {
        ChatResponse response = new ChatResponse();
        response.setRequestId(requestId);
        response.setError(error);
        response.setIsEnd(true);
        return response;
    }
}