package com.xidian.chatservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * SseEmitter 综合测试 - 参考答案
 * 
 * ⚠️ 注意：这是参考答案，请先自己完成测试，然后再对照检查！
 * 
 * 建议学习流程：
 * 1. 先自己完成 SseEmitterComprehensiveTest.java
 * 2. 运行测试，看看哪些通过了，哪些失败了
 * 3. 对照参考答案，找出问题所在
 * 4. 理解参考答案的实现方式
 * 5. 重新实现，直到所有测试通过
 */
@Slf4j
@SpringBootTest
public class SseEmitterComprehensiveTest_ANSWER {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ============================================
    // 测试用例 1：发送单条文本通知（参考答案）
    // ============================================
    @Test
    public void test01_发送单条文本通知() throws IOException, InterruptedException {
        log.info("========== 测试用例1：发送单条文本通知 ==========");
        
        // 步骤1：创建 SSE 连接（超时时间 10 秒）
        SseEmitter emitter = new SseEmitter(10000L);
        
        // 步骤2：发送文本通知
        emitter.send(SseEmitter.event()
                .name("notification")
                .data("欢迎使用实时通知系统！"));
        
        log.info("✅ 已发送文本通知");
        
        // 步骤3：关闭连接
        emitter.complete();
        
        log.info("✅ 连接已关闭");
        Thread.sleep(100);
    }

    // ============================================
    // 测试用例 2：发送多条通知（参考答案）
    // ============================================
    @Test
    public void test02_发送多条通知() throws IOException, InterruptedException {
        log.info("========== 测试用例2：发送多条通知 ==========");
        
        String[] notifications = {
            "通知1：系统启动成功",
            "通知2：数据库连接正常",
            "通知3：缓存服务就绪",
            "通知4：所有服务运行正常",
            "通知5：系统初始化完成"
        };
        
        // 步骤1：创建 SSE 连接
        SseEmitter emitter = new SseEmitter(30000L);
        
        // 步骤2：循环发送 5 条通知，每条间隔 500 毫秒
        for (int i = 0; i < notifications.length; i++) {
            emitter.send(SseEmitter.event()
                    .name("notification")
                    .data(notifications[i]));
            
            log.info("📤 已发送：{}", notifications[i]);
            
            // 间隔 500 毫秒
            Thread.sleep(500);
        }
        
        // 步骤3：关闭连接
        emitter.complete();
        
        log.info("✅ 所有通知已发送完成");
        Thread.sleep(100);
    }

    // ============================================
    // 测试用例 3：发送 JSON 格式通知（参考答案）
    // ============================================
    @Test
    public void test03_发送JSON格式通知() throws IOException, InterruptedException {
        log.info("========== 测试用例3：发送 JSON 格式通知 ==========");
        
        // 步骤1：创建 SSE 连接
        SseEmitter emitter = new SseEmitter(20000L);
        
        // 步骤2：创建 3 个 Notification 对象并发送
        Notification notif1 = new Notification("info", "系统消息", "系统运行正常");
        Notification notif2 = new Notification("warning", "警告消息", "内存使用率较高");
        Notification notif3 = new Notification("success", "成功消息", "任务执行完成");
        
        // 转换为 JSON 并发送
        String json1 = objectMapper.writeValueAsString(notif1);
        emitter.send(SseEmitter.event()
                .name("notification")
                .data(json1));
        log.info("📤 已发送 JSON 1：{}", json1);
        
        Thread.sleep(500);
        
        String json2 = objectMapper.writeValueAsString(notif2);
        emitter.send(SseEmitter.event()
                .name("notification")
                .data(json2));
        log.info("📤 已发送 JSON 2：{}", json2);
        
        Thread.sleep(500);
        
        String json3 = objectMapper.writeValueAsString(notif3);
        emitter.send(SseEmitter.event()
                .name("notification")
                .data(json3));
        log.info("📤 已发送 JSON 3：{}", json3);
        
        // 步骤3：关闭连接
        emitter.complete();
        
        log.info("✅ 所有 JSON 通知已发送完成");
        Thread.sleep(100);
    }

    // ============================================
    // 测试用例 4：流式推送长文本（参考答案）
    // ============================================
    @Test
    public void test04_流式推送长文本() throws IOException, InterruptedException {
        log.info("========== 测试用例4：流式推送长文本 ==========");
        
        String longText = "这是一个流式推送的示例，每个字符都会单独发送，就像打字一样。";
        
        // 步骤1：创建 SSE 连接
        SseEmitter emitter = new SseEmitter(60000L);
        
        // 步骤2：将文本逐字发送，每个字符间隔 100 毫秒
        char[] chars = longText.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String chunk = String.valueOf(chars[i]);
            
            emitter.send(SseEmitter.event()
                    .name("chunk")
                    .data(chunk));
            
            log.info("📤 发送字符：{}", chunk);
            
            // 间隔 100 毫秒
            Thread.sleep(100);
        }
        
        // 步骤3：关闭连接
        emitter.complete();
        
        log.info("✅ 流式推送完成");
        Thread.sleep(100);
    }

    // ============================================
    // 测试用例 5：设置回调函数（参考答案）
    // ============================================
    @Test
    public void test05_设置回调函数() throws IOException, InterruptedException {
        log.info("========== 测试用例5：设置回调函数 ==========");
        
        // 步骤1：创建 SSE 连接
        SseEmitter emitter = new SseEmitter(5000L);
        
        // 步骤2：设置三个回调函数
        emitter.onCompletion(() -> {
            log.info("🎉 连接已完成");
        });
        
        emitter.onTimeout(() -> {
            log.warn("⏰ 连接已超时");
        });
        
        emitter.onError((ex) -> {
            log.error("❌ 连接出错：{}", ex.getMessage());
        });
        
        // 步骤3：发送消息
        emitter.send(SseEmitter.event()
                .name("message")
                .data("这是一条测试消息"));
        
        log.info("✅ 已发送测试消息");
        
        // 步骤4：关闭连接（会触发 onCompletion）
        emitter.complete();
        
        Thread.sleep(200); // 等待回调执行
    }

    // ============================================
    // 测试用例 6：处理连接超时（参考答案）
    // ============================================
    @Test
    public void test06_处理连接超时() throws InterruptedException {
        log.info("========== 测试用例6：处理连接超时 ==========");
        
        // 步骤1：创建 SSE 连接（超时时间 2 秒）
        SseEmitter emitter = new SseEmitter(2000L);
        
        // 步骤2：设置超时回调
        emitter.onTimeout(() -> {
            log.warn("⏰ 连接已超时！");
        });
        
        log.info("✅ 创建了 SSE 连接，超时时间：2秒");
        log.info("⏳ 等待 3 秒，让连接超时...");
        
        // 步骤3：等待 3 秒（超过超时时间）
        Thread.sleep(3000);
        
        // 步骤4：尝试发送消息（使用 try-catch 处理异常）
        try {
            emitter.send(SseEmitter.event()
                    .name("message")
                    .data("这条消息可能发送失败"));
            log.info("✅ 消息发送成功");
        } catch (Exception e) {
            log.error("❌ 消息发送失败：{}", e.getMessage());
        }
        
        Thread.sleep(500); // 等待回调执行
    }

    // ============================================
    // 测试用例 7：错误处理（参考答案）
    // ============================================
    @Test
    public void test07_错误处理() throws IOException, InterruptedException {
        log.info("========== 测试用例7：错误处理 ==========");
        
        // 步骤1：创建 SSE 连接
        SseEmitter emitter = new SseEmitter(10000L);
        
        // 步骤2：设置错误回调
        emitter.onError((ex) -> {
            log.error("❌ 连接出错：{}", ex.getMessage());
        });
        
        // 步骤3：发送一条消息
        emitter.send(SseEmitter.event()
                .name("message")
                .data("第一条消息"));
        log.info("✅ 第一条消息发送成功");
        
        // 步骤4：关闭连接
        emitter.complete();
        log.info("✅ 连接已关闭");
        
        // 步骤5：尝试在关闭后再次发送消息（使用 try-catch）
        try {
            emitter.send(SseEmitter.event()
                    .name("message")
                    .data("这条消息会失败"));
            log.info("✅ 这条消息不应该发送成功");
        } catch (Exception e) {
            log.error("❌ 发送消息失败（预期行为）：{}", e.getMessage());
        }
        
        Thread.sleep(100);
    }

    // ============================================
    // 测试用例 8：综合场景（参考答案）
    // ============================================
    @Test
    public void test08_综合场景_实时系统监控() throws IOException, InterruptedException {
        log.info("========== 测试用例8：综合场景 - 实时系统监控 ==========");
        
        // 步骤1：创建 SSE 连接（超时时间 60 秒）
        SseEmitter emitter = new SseEmitter(60000L);
        
        // 步骤2：设置所有回调函数
        emitter.onCompletion(() -> {
            log.info("🎉 系统监控连接已完成");
        });
        
        emitter.onTimeout(() -> {
            log.warn("⏰ 系统监控连接已超时");
        });
        
        emitter.onError((ex) -> {
            log.error("❌ 系统监控连接出错：{}", ex.getMessage());
        });
        
        // 步骤3：发送系统启动通知（JSON 格式）
        Notification startNotif = new Notification("system", "系统启动", "系统正在启动...");
        String startJson = objectMapper.writeValueAsString(startNotif);
        emitter.send(SseEmitter.event()
                .name("notification")
                .data(startJson));
        log.info("📤 已发送系统启动通知");
        
        Thread.sleep(1000);
        
        // 步骤4：每隔 1 秒发送一条系统状态更新（共 5 条）
        for (int i = 1; i <= 5; i++) {
            // 模拟不同的系统状态
            double cpuUsage = 20 + Math.random() * 30;  // 20-50%
            double memoryUsage = 40 + Math.random() * 20;  // 40-60%
            double diskUsage = 50 + Math.random() * 20;  // 50-70%
            
            SystemStatus status = new SystemStatus(cpuUsage, memoryUsage, diskUsage);
            String statusJson = objectMapper.writeValueAsString(status);
            
            emitter.send(SseEmitter.event()
                    .name("status")
                    .data(statusJson));
            
            log.info("📤 已发送系统状态更新 {}：CPU={:.2f}%, Memory={:.2f}%, Disk={:.2f}%",
                    i, cpuUsage, memoryUsage, diskUsage);
            
            Thread.sleep(1000);  // 间隔 1 秒
        }
        
        // 步骤5：发送系统就绪通知（文本格式）
        emitter.send(SseEmitter.event()
                .name("notification")
                .data("系统监控完成，所有服务运行正常"));
        log.info("📤 已发送系统就绪通知");
        
        // 步骤6：关闭连接
        emitter.complete();
        
        log.info("✅ 系统监控完成");
        Thread.sleep(200);
    }

    // ============================================
    // 辅助类：通知数据模型
    // ============================================
    static class Notification {
        private String type;
        private String title;
        private String content;
        private Long timestamp;

        public Notification(String type, String title, String content) {
            this.type = type;
            this.title = title;
            this.content = content;
            this.timestamp = System.currentTimeMillis();
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public Long getTimestamp() { return timestamp; }
        public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
    }

    // ============================================
    // 辅助类：系统状态数据模型
    // ============================================
    static class SystemStatus {
        private Double cpuUsage;
        private Double memoryUsage;
        private Double diskUsage;
        private Long timestamp;

        public SystemStatus(Double cpuUsage, Double memoryUsage, Double diskUsage) {
            this.cpuUsage = cpuUsage;
            this.memoryUsage = memoryUsage;
            this.diskUsage = diskUsage;
            this.timestamp = System.currentTimeMillis();
        }

        public Double getCpuUsage() { return cpuUsage; }
        public void setCpuUsage(Double cpuUsage) { this.cpuUsage = cpuUsage; }
        public Double getMemoryUsage() { return memoryUsage; }
        public void setMemoryUsage(Double memoryUsage) { this.memoryUsage = memoryUsage; }
        public Double getDiskUsage() { return diskUsage; }
        public void setDiskUsage(Double diskUsage) { this.diskUsage = diskUsage; }
        public Long getTimestamp() { return timestamp; }
        public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
    }
}

