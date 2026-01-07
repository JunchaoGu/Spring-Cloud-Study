package com.xidian.chatservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/**
 * SSE 学习控制器
 * 
 * 这个控制器专门用于学习和测试 SSE 功能
 * 你可以在浏览器中直接访问这些接口来观察 SSE 的效果
 * 
 * 如何使用：
 * 1. 启动应用
 * 2. 在浏览器中访问对应的 URL
 * 3. 观察浏览器中接收到的消息
 * 
 * 注意：浏览器会显示接收到的数据，就像看直播一样
 */
@Slf4j
@RestController
@RequestMapping("/api/learn/sse")
public class SseLearningController {

    /**
     * 示例1：最简单的 SSE 连接
     * 
     * 访问地址：http://localhost:6001/api/learn/sse/simple
     * 
     * 功能：发送3条简单的文本消息
     */
    @GetMapping(value = "/simple", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter simpleExample() {
        log.info("📡 收到 SSE 连接请求：/simple");
        
        // 创建 SSE 连接，超时时间30秒
        SseEmitter emitter = new SseEmitter(30000L);
        
        // 设置回调函数
        emitter.onCompletion(() -> log.info("✅ 连接已完成"));
        emitter.onTimeout(() -> log.warn("⏰ 连接已超时"));
        emitter.onError((ex) -> log.error("❌ 连接出错：{}", ex.getMessage()));
        
        // 在新线程中发送消息（避免阻塞主线程）
        new Thread(() -> {
            try {
                // 发送第1条消息
                emitter.send(SseEmitter.event()
                        .name("greeting")
                        .data("你好！这是第一条消息"));
                log.info("📤 发送了第1条消息");
                
                Thread.sleep(1000);  // 等待1秒
                
                // 发送第2条消息
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data("这是第二条消息"));
                log.info("📤 发送了第2条消息");
                
                Thread.sleep(1000);  // 等待1秒
                
                // 发送第3条消息
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data("这是第三条消息，连接即将关闭"));
                log.info("📤 发送了第3条消息");
                
                Thread.sleep(1000);  // 等待1秒
                
                // 关闭连接
                emitter.complete();
                log.info("✅ 连接已关闭");
                
            } catch (Exception e) {
                log.error("❌ 发送消息时出错：{}", e.getMessage());
                emitter.completeWithError(e);
            }
        }).start();
        
        return emitter;
    }

    /**
     * 示例2：流式发送消息（模拟打字效果）
     * 
     * 访问地址：http://localhost:6001/api/learn/sse/stream?text=你好世界
     * 
     * 功能：将你输入的文本逐字发送，就像打字一样
     * 
     * @param text 要发送的文本（可选，默认值："Hello, SSE!"）
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamExample(@RequestParam(defaultValue = "Hello, SSE!") String text) {
        log.info("📡 收到 SSE 连接请求：/stream, text={}", text);
        
        SseEmitter emitter = new SseEmitter(60000L);
        
        emitter.onCompletion(() -> log.info("✅ 流式发送完成"));
        emitter.onTimeout(() -> log.warn("⏰ 连接超时"));
        emitter.onError((ex) -> log.error("❌ 连接出错：{}", ex.getMessage()));
        
        // 在新线程中逐字发送
        new Thread(() -> {
            try {
                log.info("🤖 开始流式发送：{}", text);
                
                // 将文本拆分成字符数组
                char[] chars = text.toCharArray();
                
                for (int i = 0; i < chars.length; i++) {
                    String chunk = String.valueOf(chars[i]);
                    
                    // 发送单个字符
                    emitter.send(SseEmitter.event()
                            .name("chunk")
                            .data(chunk));
                    
                    log.info("📤 发送了第 {} 个字符：{}", i + 1, chunk);
                    
                    // 等待200毫秒（模拟打字速度）
                    Thread.sleep(200);
                }
                
                // 发送结束标志
                emitter.send(SseEmitter.event()
                        .name("end")
                        .data("\n\n✅ 发送完成！"));
                
                log.info("✅ 流式发送完成");
                
                // 关闭连接
                emitter.complete();
                
            } catch (Exception e) {
                log.error("❌ 发送消息时出错：{}", e.getMessage());
                emitter.completeWithError(e);
            }
        }).start();
        
        return emitter;
    }

    /**
     * 示例3：定时发送消息（模拟实时数据更新）
     * 
     * 访问地址：http://localhost:6001/api/learn/sse/timer?count=10
     * 
     * 功能：每隔1秒发送一条消息，模拟实时数据更新（如股票价格、温度等）
     * 
     * @param count 要发送的消息数量（默认10条）
     */
    @GetMapping(value = "/timer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter timerExample(@RequestParam(defaultValue = "10") int count) {
        log.info("📡 收到 SSE 连接请求：/timer, count={}", count);
        
        SseEmitter emitter = new SseEmitter(120000L);  // 2分钟超时
        
        emitter.onCompletion(() -> log.info("✅ 定时发送完成"));
        emitter.onTimeout(() -> log.warn("⏰ 连接超时"));
        emitter.onError((ex) -> log.error("❌ 连接出错：{}", ex.getMessage()));
        
        // 在新线程中定时发送
        new Thread(() -> {
            try {
                for (int i = 1; i <= count; i++) {
                    // 模拟数据（比如温度、价格等）
                    double value = 20 + Math.random() * 10;  // 20-30之间的随机数
                    
                    String message = String.format("第 %d 次更新：当前值 = %.2f", i, value);
                    
                    emitter.send(SseEmitter.event()
                            .name("update")
                            .data(message));
                    
                    log.info("📤 {}", message);
                    
                    // 等待1秒
                    Thread.sleep(1000);
                }
                
                // 发送结束标志
                emitter.send(SseEmitter.event()
                        .name("end")
                        .data("✅ 所有更新已完成"));
                
                log.info("✅ 定时发送完成");
                
                // 关闭连接
                emitter.complete();
                
            } catch (Exception e) {
                log.error("❌ 发送消息时出错：{}", e.getMessage());
                emitter.completeWithError(e);
            }
        }).start();
        
        return emitter;
    }

    /**
     * 示例4：发送 JSON 格式的数据
     * 
     * 访问地址：http://localhost:6001/api/learn/sse/json
     * 
     * 功能：发送 JSON 格式的消息（实际项目中常用）
     */
    @GetMapping(value = "/json", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter jsonExample() {
        log.info("📡 收到 SSE 连接请求：/json");
        
        SseEmitter emitter = new SseEmitter(30000L);
        
        emitter.onCompletion(() -> log.info("✅ JSON 发送完成"));
        emitter.onTimeout(() -> log.warn("⏰ 连接超时"));
        emitter.onError((ex) -> log.error("❌ 连接出错：{}", ex.getMessage()));
        
        // 在新线程中发送 JSON 数据
        new Thread(() -> {
            try {
                // 发送第1条 JSON 消息
                String json1 = "{\"userId\":\"user123\",\"message\":\"你好\",\"timestamp\":" + System.currentTimeMillis() + "}";
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(json1));
                log.info("📤 发送了 JSON 1：{}", json1);
                
                Thread.sleep(1000);
                
                // 发送第2条 JSON 消息
                String json2 = "{\"userId\":\"user123\",\"message\":\"再见\",\"timestamp\":" + System.currentTimeMillis() + "}";
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(json2));
                log.info("📤 发送了 JSON 2：{}", json2);
                
                Thread.sleep(1000);
                
                // 发送结束标志
                emitter.send(SseEmitter.event()
                        .name("end")
                        .data("{\"status\":\"completed\"}"));
                
                log.info("✅ JSON 发送完成");
                
                // 关闭连接
                emitter.complete();
                
            } catch (Exception e) {
                log.error("❌ 发送消息时出错：{}", e.getMessage());
                emitter.completeWithError(e);
            }
        }).start();
        
        return emitter;
    }

    /**
     * 示例5：模拟聊天场景
     * 
     * 访问地址：http://localhost:6001/api/learn/sse/chat?question=什么是Java
     * 
     * 功能：模拟 AI 回答问题的场景，逐字返回答案
     * 
     * @param question 用户的问题
     */
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatExample(@RequestParam(defaultValue = "你好") String question) {
        log.info("📡 收到 SSE 连接请求：/chat, question={}", question);
        
        SseEmitter emitter = new SseEmitter(60000L);
        
        emitter.onCompletion(() -> log.info("✅ 聊天完成"));
        emitter.onTimeout(() -> log.warn("⏰ 连接超时"));
        emitter.onError((ex) -> log.error("❌ 连接出错：{}", ex.getMessage()));
        
        // 在新线程中模拟 AI 回答
        new Thread(() -> {
            try {
                // 模拟 AI 的回答（这里只是示例，实际应该调用真实的 AI API）
                String answer = "这是一个关于 \"" + question + "\" 的回答。在实际项目中，这里会调用大模型 API 来生成回答。";
                
                log.info("🤖 开始回答：{}", answer);
                
                // 逐字发送答案（模拟流式效果）
                char[] chars = answer.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    String chunk = String.valueOf(chars[i]);
                    
                    emitter.send(SseEmitter.event()
                            .name("chunk")
                            .data(chunk));
                    
                    // 等待100毫秒（模拟打字速度）
                    Thread.sleep(100);
                }
                
                // 发送结束标志
                emitter.send(SseEmitter.event()
                        .name("end")
                        .data("\n\n✅ 回答完成"));
                
                log.info("✅ 回答完成");
                
                // 关闭连接
                emitter.complete();
                
            } catch (Exception e) {
                log.error("❌ 发送消息时出错：{}", e.getMessage());
                emitter.completeWithError(e);
            }
        }).start();
        
        return emitter;
    }
}

