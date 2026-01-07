package com.xidian.chatservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * SseEmitter 学习测试类
 * 
 * 什么是 SSE (Server-Sent Events)？
 * ============================================
 * SSE 是一种服务器向客户端推送数据的技术。
 * 就像你订阅了一个频道，服务器会主动给你推送消息，而不需要你不停地问"有新消息吗？"
 * 
 * SseEmitter 是什么？
 * ============================================
 * SseEmitter 是 Spring 提供的工具，用来建立和管理 SSE 连接。
 * 想象一下：
 * - 前端（浏览器）打开一个连接，就像打开了一个"水管"
 * - 后端（服务器）通过这个"水管"不断发送数据
 * - 前端可以实时接收到这些数据，就像看直播一样
 * 
 * 使用场景：
 * - 聊天消息推送
 * - 实时数据更新（股票价格、天气等）
 * - 进度条更新
 * - 通知推送
 */
@Slf4j
@SpringBootTest
public class SseEmitterLearningTest {

    /**
     * 示例 1：最简单的 SSE 连接
     * 
     * 这个例子展示了如何：
     * 1. 创建一个 SseEmitter 对象
     * 2. 发送一条消息
     * 3. 关闭连接
     */
    @Test
    public void test01_最简单的SSE连接() throws IOException, InterruptedException {
        log.info("========== 示例1：最简单的 SSE 连接 ==========");
        
        // 步骤1：创建一个 SseEmitter 对象
        // 参数 30000 表示超时时间（30秒），如果30秒内没有发送数据，连接会自动关闭
        SseEmitter emitter = new SseEmitter(30000L);
        
        log.info("✅ 创建了 SSE 连接，超时时间：30秒");
        
        // 步骤2：发送一条消息
        // SseEmitter.event() 创建一个事件                                    返回的是内部类的 内部接口SseEmitterBuilder的内部实现类；建造者模式
        // .data("你好，这是第一条消息") 设置要发送的数据
        // .name("greeting") 设置事件名称（可选，前端可以根据名称区分不同的事件）
        emitter.send(SseEmitter.event()
                .name("greeting")  // 事件名称
                .data("你好，这是第一条消息"));  // 要发送的数据
        
        log.info("✅ 发送了第一条消息");
        
        // 步骤3：再发送一条消息
        emitter.send(SseEmitter.event()
                .data("这是第二条消息"));
        
        log.info("✅ 发送了第二条消息");
        
        // 步骤4：关闭连接
        // complete() 表示正常完成，前端会收到连接关闭的通知
        emitter.complete();
        
        log.info("✅ 连接已关闭");
        log.info("========== 示例1 完成 ==========\n");
    }

    /**
     * 示例 2：流式发送多条消息（模拟聊天）
     * 
     * 这个例子展示了如何：
     * 1. 每隔一段时间发送一条消息
     * 2. 模拟流式响应的效果
     */
    @Test
    public void test02_流式发送多条消息() throws IOException, InterruptedException {
        log.info("========== 示例2：流式发送多条消息 ==========");
        
        // 创建一个超时时间为60秒的连接
        SseEmitter emitter = new SseEmitter(60000L);
        
        // 模拟发送5条消息，每条消息间隔1秒
        String[] messages = {
            "你好！",
            "我是AI助手",
            "我可以帮你解答问题",
            "有什么需要帮助的吗？",
            "祝你有美好的一天！"
        };
        
        for (int i = 0; i < messages.length; i++) {
            // 发送消息
            emitter.send(SseEmitter.event()
                    .name("message")  // 事件名称
                    .data("第" + (i + 1) + "条：" + messages[i]));  // 消息内容
            
            log.info("📤 发送了第 {} 条消息：{}", i + 1, messages[i]);
            
            // 等待1秒再发送下一条（模拟流式效果）
            Thread.sleep(1000);
        }
        
        // 发送结束标志
        emitter.send(SseEmitter.event()
                .name("end")
                .data("所有消息已发送完毕"));
        
        log.info("✅ 发送了结束标志");
        
        // 关闭连接
        emitter.complete();
        
        log.info("✅ 连接已关闭");
        log.info("========== 示例2 完成 ==========\n");
    }

    /**
     * 示例 3：使用回调函数处理连接事件
     * 
     * 这个例子展示了如何：
     * 1. 监听连接完成事件
     * 2. 监听连接超时事件
     * 3. 监听连接错误事件
     *
     * onTimeOut
     * onError
     * onCompletion
     */
    @Test
    public void test03_使用回调函数() throws IOException, InterruptedException {
        log.info("========== 示例3：使用回调函数 ==========");
        
        SseEmitter emitter = new SseEmitter(5000L);  // 5秒超时
        
        // 回调1：连接完成时触发（正常关闭）
        emitter.onCompletion(() -> {
            log.info("🎉 连接已完成（正常关闭）");
        });
        
        // 回调2：连接超时时触发
        emitter.onTimeout(() -> {
            log.warn("⏰ 连接已超时（超过5秒未完成）");
        });
        
        // 回调3：连接出错时触发
        emitter.onError((ex) -> {
            log.error("❌ 连接出错：{}", ex.getMessage());
        });
        
        // 发送一条消息
        emitter.send(SseEmitter.event().data("这是一条测试消息"));
        log.info("✅ 发送了测试消息");
        
        // 立即关闭连接（会触发 onCompletion 回调）
        emitter.complete();
        
        // 等待一下，让回调函数执行
        Thread.sleep(100);
        
        log.info("========== 示例3 完成 ==========\n");
    }


    /**
     * 示例 4：模拟超时场景
     * 
     * 这个例子展示了当连接超时会发生什么
     */
    @Test
    public void test04_模拟超时场景() throws InterruptedException {
        log.info("========== 示例4：模拟超时场景 ==========");
        
        // 创建一个超时时间为2秒的连接
        SseEmitter emitter = new SseEmitter(2000L);
        
        // 设置超时回调
        emitter.onTimeout(() -> {
            log.warn("⏰ 连接已超时！");
        });
        
        // 设置完成回调
        emitter.onCompletion(() -> {
            log.info("🎉 连接已完成");
        });
        
        // 等待3秒（超过9秒的超时时间）
        Thread.sleep(9000);
        
        // 尝试发送消息（此时连接可能已经超时）
        try {
            emitter.send(SseEmitter.event().data("这条消息可能发送失败"));
            log.info("✅ 消息发送成功");
        } catch (Exception e) {
            log.error("❌ 消息发送失败：{}", e.getMessage());
        }
        
        log.info("========== 示例4 完成 ==========\n");
    }

    /**
     * 示例 5：发送 JSON 格式的数据
     * 
     * 实际项目中，我们通常发送 JSON 格式的数据
     * 这个例子展示了如何发送 JSON
     */
    @Test
    public void test05_发送JSON数据() throws IOException {
        log.info("========== 示例5：发送 JSON 数据 ==========");
        
        SseEmitter emitter = new SseEmitter(30000L);
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 创建一个简单的数据对象
        MessageData data1 = new MessageData("user123", "你好", System.currentTimeMillis());
        
        // 将对象转换为 JSON 字符串
        String json1 = objectMapper.writeValueAsString(data1);
        
        // 发送 JSON 数据
        emitter.send(SseEmitter.event()
                .name("message")
                .data(json1));
        
        log.info("📤 发送了 JSON 数据：{}", json1);
        
        // 再发送一条
        MessageData data2 = new MessageData("user123", "再见", System.currentTimeMillis());
        String json2 = objectMapper.writeValueAsString(data2);
        emitter.send(SseEmitter.event()
                .name("message")
                .data(json2));
        
        log.info("📤 发送了 JSON 数据：{}", json2);
        
        emitter.complete();
        log.info("✅ 连接已关闭");
        log.info("========== 示例5 完成 ==========\n");
    }

    /**
     * 示例 6：模拟实际聊天场景
     * 
     * 这个例子模拟了实际的聊天场景：
     * 1. 用户发送问题
     * 2. AI 流式返回回答（一个字一个字地返回）
     */
    @Test
    public void test06_模拟聊天场景() throws IOException, InterruptedException {
        log.info("========== 示例6：模拟聊天场景 ==========");
        
        SseEmitter emitter = new SseEmitter(60000L);
        
        // 模拟用户的问题
        String question = "请介绍一下Java";
        log.info("👤 用户问题：{}", question);
        
        // 模拟 AI 的回答（逐字返回）
        String answer = "Java是一种面向对象的编程语言，由Sun公司开发，现在属于Oracle公司。";
        log.info("🤖 AI 开始回答...");
        
        // 将回答拆分成字符，逐个发送（模拟流式效果）
        for (int i = 0; i < answer.length(); i++) {
            String chunk = String.valueOf(answer.charAt(i));
            
            // 发送一个字符
            emitter.send(SseEmitter.event()
                    .name("chunk")  // 事件名称：chunk（片段）
                    .data(chunk));  // 发送单个字符
            
            log.info("📤 发送片段：{}", chunk);
            
            // 等待100毫秒，模拟网络延迟
            Thread.sleep(100);
        }
        
        // 发送结束标志
        emitter.send(SseEmitter.event()
                .name("end")
                .data("回答完毕"));
        
        log.info("✅ AI 回答完成");
        
        emitter.complete();
        log.info("✅ 连接已关闭");
        log.info("========== 示例6 完成 ==========\n");
    }

    /**
     * 示例 7：错误处理
     * 
     * 这个例子展示了如何处理发送错误
     */
    @Test
    public void test07_错误处理() {
        log.info("========== 示例7：错误处理 ==========");
        
        SseEmitter emitter = new SseEmitter(30000L);
        
        // 设置错误回调
        emitter.onError((ex) -> {
            log.error("❌ 连接出错：{}", ex.getMessage());
        });
        
        try {
            // 正常发送消息
            emitter.send(SseEmitter.event().data("第一条消息"));
            log.info("✅ 第一条消息发送成功");
            
            // 关闭连接
            emitter.complete();
            log.info("✅ 连接已关闭");
            
            // 尝试在关闭后发送消息（会失败）
            emitter.send(SseEmitter.event().data("这条消息会失败"));
            log.info("✅ 这条消息不应该发送成功");
            
        } catch (Exception e) {
            log.error("❌ 发送消息失败：{}", e.getMessage());
        }
        
        log.info("========== 示例7 完成 ==========\n");
    }

    /**
     * 示例 8：完整的使用流程
     * 
     * 这个例子展示了完整的使用流程，包括：
     * 1. 创建连接
     * 2. 设置回调【可以理解为一种配置，可以优先设置】
     * 3. 发送数据
     * 4. 处理错误
     * 5. 关闭连接
     */
    @Test
    public void test08_完整使用流程() throws IOException, InterruptedException {
        log.info("========== 示例8：完整使用流程 ==========");
        
        // ========== 步骤1：创建连接 ==========
        long timeout = 30000L;  // 30秒超时
        SseEmitter emitter = new SseEmitter(timeout);
        log.info("步骤1：创建了 SSE 连接，超时时间：{} 秒", timeout / 1000);
        
        // ========== 步骤2：设置回调函数 ==========
        emitter.onCompletion(() -> {
            log.info("步骤5：连接已完成（正常关闭）");
        });
        
        emitter.onTimeout(() -> {
            log.warn("步骤5：连接已超时");
        });
        
        emitter.onError((ex) -> {
            log.error("步骤5：连接出错 - {}", ex.getMessage());
        });
        log.info("步骤2：设置了回调函数（完成、超时、错误）");


        // ========== 步骤3：发送数据 ==========
        String[] messages = {"消息1", "消息2", "消息3"};
        for (int i = 0; i < messages.length; i++) {
            emitter.send(SseEmitter.event()
                    .name("message")
                    .data(messages[i]));
            log.info("步骤3：发送了第 {} 条消息：{}", i + 1, messages[i]);
            Thread.sleep(500);  // 等待0.5秒
        }
        
        // ========== 步骤4：发送结束标志 ==========
        emitter.send(SseEmitter.event()
                .name("end")
                .data("所有消息已发送完毕"));
        log.info("步骤4：发送了结束标志");
        
        // ========== 步骤5：关闭连接 ==========
        emitter.complete();
        log.info("步骤5：关闭了连接（会触发 onCompletion 回调）");
        
        // 等待一下，让回调函数执行
        Thread.sleep(100);
        
        log.info("========== 示例8 完成 ==========\n");
    }

    /**
     * 辅助类：用于示例5的 JSON 数据
     */
    static class MessageData {
        private String userId;
        private String message;
        private Long timestamp;

        public MessageData(String userId, String message, Long timestamp) {
            this.userId = userId;
            this.message = message;
            this.timestamp = timestamp;
        }

        // Getters and Setters (Jackson 需要)
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Long getTimestamp() { return timestamp; }
        public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
    }
}

