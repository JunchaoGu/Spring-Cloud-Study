package com.xidian.chatservice.service.impl;

import com.xidian.chatservice.model.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;

import com.xidian.chatservice.service.LLMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/**
 * 大模型服务实现（使用 Spring AI + Dashscope）
 */
@Slf4j
@Service
public class LLMServiceImpl implements LLMService {

    private final ChatClient chatClient;

    // 通过构造函数注入 ChatClient.Builder，然后构建 ChatClient
    public LLMServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public void streamChat(ChatRequest request, Consumer<String> onChunk) throws Exception {
        log.info("📞 调用大模型 API (Spring AI): {}", request.getRequestId());

        try {
            // 使用 Spring AI 的流式调用
            Flux<String> contentFlux = chatClient.prompt()
                    .user(request.getQuestion())
                    .stream()
                    .content();

            // 阻塞式消费流式响应，每个 chunk 调用回调函数
            contentFlux
                    .doOnNext(chunk -> {
                        if (chunk != null && !chunk.isEmpty()) {
                            log.debug("📤 收到 chunk: {}", chunk);
                            onChunk.accept(chunk);
                        }
                    })
                    .doOnComplete(() -> log.info("✅ 大模型响应完成: {}", request.getRequestId()))
                    .doOnError(e -> log.error("❌ 大模型调用失败: {}, error: {}",
                            request.getRequestId(), e.getMessage()))
                    .blockLast();  // 阻塞等待完成

        } catch (Exception e) {
            log.error("❌ 大模型调用异常: {}", e.getMessage(), e);
            throw e;
        }
    }
}