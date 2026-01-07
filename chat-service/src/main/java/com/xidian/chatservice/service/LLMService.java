package com.xidian.chatservice.service;

import com.xidian.chatservice.model.ChatRequest;

import java.util.function.Consumer;

/**
 * 大模型服务接口
 */
public interface LLMService {

    /**
     * 流式调用大模型 API
     *
     * @param request 请求对象
     * @param onChunk 每收到一个片段时的回调函数
     * @throws Exception 调用异常
     */
    void streamChat(ChatRequest request, Consumer<String> onChunk) throws Exception;
}