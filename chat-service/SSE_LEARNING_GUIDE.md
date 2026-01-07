# SSE (Server-Sent Events) 学习指南

## 📚 什么是 SSE？

**SSE (Server-Sent Events)** 是一种服务器向客户端推送数据的技术。

### 简单理解
想象一下：
- **传统方式**：你不停地问服务器"有新消息吗？"（轮询）
- **SSE 方式**：你打开一个"水管"，服务器主动通过这个"水管"给你推送消息（推送）

就像：
- 📺 **看电视**：电视台主动推送节目给你（SSE）
- 📞 **打电话**：你需要主动打电话问对方（轮询）

### 使用场景
- 💬 聊天消息推送
- 📊 实时数据更新（股票价格、温度等）
- 📈 进度条更新
- 🔔 通知推送

---

## 🚀 如何开始学习？

### 方法1：运行单元测试（推荐）

1. **打开测试文件**
   ```
   chat-service/src/test/java/com/xidian/chatservice/SseEmitterLearningTest.java
   ```

2. **运行测试**
   - 在 IDE 中右键点击测试类或测试方法
   - 选择 "Run" 或 "运行"
   - 观察控制台输出

3. **学习内容**
   - 示例1：最简单的 SSE 连接
   - 示例2：流式发送多条消息
   - 示例3：使用回调函数
   - 示例4：模拟超时场景
   - 示例5：发送 JSON 格式的数据
   - 示例6：模拟聊天场景
   - 示例7：错误处理
   - 示例8：完整使用流程

### 方法2：在浏览器中测试（最直观）

1. **启动应用**
   ```bash
   cd chat-service
   mvn spring-boot:run
   ```

2. **打开测试页面**
   在浏览器中访问：
   ```
   http://localhost:6001/sse-test.html
   ```

3. **测试不同的示例**
   - 点击各个示例的"开始测试"按钮
   - 观察"输出区域"中接收到的消息
   - 尝试修改参数（如文本内容、消息数量等）

### 方法3：使用 API 测试工具（如 Postman）

1. **启动应用**

2. **测试接口**

   **示例1：最简单的 SSE**
   ```
   GET http://localhost:6001/api/learn/sse/simple
   ```

   **示例2：流式发送**
   ```
   GET http://localhost:6001/api/learn/sse/stream?text=你好世界
   ```

   **示例3：定时发送**
   ```
   GET http://localhost:6001/api/learn/sse/timer?count=10
   ```

   **示例4：JSON 数据**
   ```
   GET http://localhost:6001/api/learn/sse/json
   ```

   **示例5：模拟聊天**
   ```
   GET http://localhost:6001/api/learn/sse/chat?question=什么是Java
   ```

---

## 📖 核心概念

### 1. SseEmitter 对象

```java
// 创建一个 SSE 连接，超时时间30秒
SseEmitter emitter = new SseEmitter(30000L);
```

**参数说明**：
- `30000L`：超时时间（毫秒），30秒后如果还没完成，连接会自动关闭

### 2. 发送消息

```java
// 发送一条消息
emitter.send(SseEmitter.event()
    .name("message")      // 事件名称（可选）
    .data("你好"));        // 要发送的数据
```

**说明**：
- `.name()`：事件名称，前端可以根据名称区分不同的事件
- `.data()`：要发送的数据（可以是字符串、JSON等）

### 3. 关闭连接

```java
// 正常关闭
emitter.complete();

// 异常关闭
emitter.completeWithError(new Exception("错误信息"));
```

### 4. 回调函数

```java
// 连接完成时触发
emitter.onCompletion(() -> {
    System.out.println("连接已完成");
});

// 连接超时时触发
emitter.onTimeout(() -> {
    System.out.println("连接已超时");
});

// 连接出错时触发
emitter.onError((ex) -> {
    System.out.println("连接出错：" + ex.getMessage());
});
```

---

## 🔍 常见问题

### Q1: SSE 和 WebSocket 有什么区别？

| 特性 | SSE | WebSocket |
|------|-----|-----------|
| 通信方向 | 单向（服务器→客户端） | 双向（服务器↔客户端） |
| 协议 | HTTP | 独立的 WebSocket 协议 |
| 复杂度 | 简单 | 相对复杂 |
| 适用场景 | 服务器推送数据 | 需要双向通信（如游戏、聊天） |

**简单理解**：
- **SSE**：像看电视，电视台推送节目给你
- **WebSocket**：像打电话，双方可以互相说话

### Q2: 什么时候使用 SSE？

✅ **适合使用 SSE**：
- 服务器需要主动推送数据给客户端
- 不需要客户端频繁发送数据
- 需要简单的实现方式

❌ **不适合使用 SSE**：
- 需要客户端频繁发送数据（应该用 WebSocket）
- 需要双向实时通信

### Q3: SSE 连接会一直保持吗？

不会。SSE 连接有以下情况会关闭：
1. **正常关闭**：调用 `emitter.complete()`
2. **超时关闭**：超过设定的超时时间
3. **错误关闭**：发生错误时
4. **客户端关闭**：浏览器关闭页面或断开连接

### Q4: 如何在前端接收 SSE 消息？

```javascript
// 创建 EventSource 对象
const eventSource = new EventSource('/api/learn/sse/simple');

// 监听消息
eventSource.addEventListener('message', function(e) {
    console.log('收到消息：', e.data);
});

// 监听特定事件
eventSource.addEventListener('greeting', function(e) {
    console.log('收到问候：', e.data);
});

// 监听错误
eventSource.onerror = function(e) {
    console.log('连接出错');
};

// 关闭连接
eventSource.close();
```

---

## 📝 学习路径建议

1. **第一步**：运行 `SseEmitterLearningTest.java` 中的示例1-3
   - 理解基本概念
   - 了解如何创建连接、发送消息、关闭连接

2. **第二步**：在浏览器中测试 `sse-test.html`
   - 直观地看到 SSE 的效果
   - 理解前端如何接收消息

3. **第三步**：阅读 `SseLearningController.java` 的代码
   - 理解实际项目中如何使用
   - 学习如何处理参数、错误等

4. **第四步**：运行示例4-8
   - 学习高级特性（超时、错误处理、JSON等）
   - 理解完整的使用流程

5. **第五步**：查看实际项目代码
   - `StreamController.java`：实际项目中的 SSE 控制器
   - `SseEmitterManager.java`：SSE 连接管理器
   - `ChatRequestListener.java`：Kafka 消费者中使用 SSE

---

## 🎯 下一步

掌握了 SSE 的基本用法后，你可以：

1. **查看实际项目代码**
   - 了解如何在 Kafka 消费者中使用 SSE
   - 学习如何管理多个 SSE 连接

2. **尝试修改代码**
   - 修改超时时间
   - 添加新的事件类型
   - 实现自己的业务逻辑

3. **深入学习**
   - 了解 SSE 的协议细节
   - 学习如何处理并发连接
   - 研究性能优化

---

## 📚 参考资源

- [MDN: Server-Sent Events](https://developer.mozilla.org/zh-CN/docs/Web/API/Server-sent_events)
- [Spring 官方文档：SseEmitter](https://docs.spring.io/spring-framework/reference/web/servlet/mvc/ann-async/sse.html)

---

**祝你学习愉快！** 🎉

