# SseEmitter 综合测试指南

## 📋 测试概述

这是一个综合性的测试类，用于验证你对 SseEmitter 的掌握程度。

**测试文件**：`SseEmitterComprehensiveTest.java`  
**参考答案**：`SseEmitterComprehensiveTest_ANSWER.java`

---

## 🎯 测试场景

**需求：实现一个实时通知系统**

你需要实现一个能够向用户推送不同类型通知的系统，包括：
- 文本通知
- JSON 格式的结构化通知
- 流式推送长文本
- 连接管理和错误处理

---

## 📝 测试用例说明

### 测试用例 1：发送单条文本通知（基础题 ⭐）
**难度**：⭐  
**知识点**：创建连接、发送消息、关闭连接

**要求**：
- 创建 SSE 连接（超时 10 秒）
- 发送一条文本消息
- 正常关闭连接

**提示**：
```java
SseEmitter emitter = new SseEmitter(10000L);
emitter.send(SseEmitter.event().data("消息内容"));
emitter.complete();
```

---

### 测试用例 2：发送多条通知（进阶题 ⭐⭐）
**难度**：⭐⭐  
**知识点**：循环发送、时间间隔

**要求**：
- 创建 SSE 连接（超时 30 秒）
- 发送 5 条通知，每条间隔 500 毫秒
- 发送完成后关闭连接

**提示**：
```java
for (int i = 0; i < notifications.length; i++) {
    emitter.send(...);
    Thread.sleep(500);
}
```

---

### 测试用例 3：发送 JSON 格式通知（进阶题 ⭐⭐）
**难度**：⭐⭐  
**知识点**：JSON 序列化、对象转换

**要求**：
- 创建 SSE 连接（超时 20 秒）
- 发送 3 条 JSON 格式的通知
- 使用 ObjectMapper 转换对象为 JSON

**提示**：
```java
Notification notif = new Notification("info", "标题", "内容");
String json = objectMapper.writeValueAsString(notif);
emitter.send(SseEmitter.event().data(json));
```

---

### 测试用例 4：流式推送长文本（进阶题 ⭐⭐）
**难度**：⭐⭐  
**知识点**：字符数组、逐字发送

**要求**：
- 创建 SSE 连接（超时 60 秒）
- 将长文本逐字发送
- 每个字符间隔 100 毫秒

**提示**：
```java
char[] chars = text.toCharArray();
for (char c : chars) {
    emitter.send(SseEmitter.event().data(String.valueOf(c)));
    Thread.sleep(100);
}
```

---

### 测试用例 5：设置回调函数（进阶题 ⭐⭐）
**难度**：⭐⭐  
**知识点**：回调函数、事件处理

**要求**：
- 创建 SSE 连接（超时 5 秒）
- 设置三个回调函数（完成、超时、错误）
- 发送消息并关闭连接

**提示**：
```java
emitter.onCompletion(() -> log.info("完成"));
emitter.onTimeout(() -> log.warn("超时"));
emitter.onError((ex) -> log.error("错误"));
```

---

### 测试用例 6：处理连接超时（高级题 ⭐⭐⭐）
**难度**：⭐⭐⭐  
**知识点**：超时处理、异常处理

**要求**：
- 创建 SSE 连接（超时 2 秒）
- 设置超时回调
- 等待 3 秒后尝试发送消息

**提示**：
```java
SseEmitter emitter = new SseEmitter(2000L);
emitter.onTimeout(() -> log.warn("超时"));
Thread.sleep(3000);
// 尝试发送消息
```

---

### 测试用例 7：错误处理（高级题 ⭐⭐⭐）
**难度**：⭐⭐⭐  
**知识点**：异常处理、连接状态

**要求**：
- 创建 SSE 连接
- 发送消息并关闭连接
- 尝试在关闭后再次发送消息（应该失败）

**提示**：
```java
emitter.complete();
try {
    emitter.send(...);  // 会失败
} catch (Exception e) {
    log.error("发送失败");
}
```

---

### 测试用例 8：综合场景（综合题 ⭐⭐⭐⭐）
**难度**：⭐⭐⭐⭐  
**知识点**：综合运用所有知识点

**要求**：
- 实现完整的系统监控通知功能
- 包括：启动通知、状态更新、就绪通知
- 使用 JSON 和文本两种格式
- 设置所有回调函数

**提示**：
- 结合前面所有测试用例的知识点
- 注意消息格式的切换
- 注意时间间隔的控制

---

## 🚀 如何开始

### 步骤 1：打开测试文件
```
chat-service/src/test/java/com/xidian/chatservice/SseEmitterComprehensiveTest.java
```

### 步骤 2：按顺序完成测试用例
建议按照测试用例 1 → 8 的顺序完成，因为：
- 前面的测试用例比较简单，是基础
- 后面的测试用例会用到前面的知识点
- 综合题需要前面所有知识点的结合

### 步骤 3：运行测试
- 在 IDE 中右键点击测试方法
- 选择 "Run" 运行单个测试
- 或者运行整个测试类

### 步骤 4：查看结果
- ✅ 测试通过：说明实现正确
- ❌ 测试失败：查看错误信息，找出问题

### 步骤 5：对照参考答案
- 完成所有测试后，可以查看参考答案
- 对比自己的实现和参考答案
- 理解不同的实现方式

---

## 📊 评分标准

| 通过测试用例数 | 评分 | 说明 |
|--------------|------|------|
| 8/8 | ⭐⭐⭐⭐⭐ 优秀 | 完全掌握 SseEmitter |
| 6-7/8 | ⭐⭐⭐⭐ 良好 | 基本掌握，还有提升空间 |
| 4-5/8 | ⭐⭐⭐ 及格 | 需要继续学习 |
| 0-3/8 | ⭐⭐ 需努力 | 建议重新学习基础 |

---

## 💡 学习建议

### 如果测试用例 1-2 失败
- 重新学习 SseEmitter 的基本用法
- 查看 `SseEmitterLearningTest.java` 的示例 1-2
- 理解创建连接、发送消息、关闭连接的流程

### 如果测试用例 3 失败
- 学习 JSON 序列化的使用
- 查看 `SseEmitterLearningTest.java` 的示例 5
- 理解 ObjectMapper 的使用

### 如果测试用例 4 失败
- 学习字符数组的处理
- 查看 `SseEmitterLearningTest.java` 的示例 6
- 理解流式推送的实现

### 如果测试用例 5-7 失败
- 学习回调函数的使用
- 查看 `SseEmitterLearningTest.java` 的示例 3、4、7
- 理解异常处理和连接状态管理

### 如果测试用例 8 失败
- 综合复习前面的知识点
- 理解如何组合使用不同的功能
- 注意代码的组织和逻辑

---

## 🔍 常见问题

### Q1: 测试运行后没有输出？
**A**: 检查是否正确创建了 SseEmitter 对象，是否正确调用了 send() 方法。

### Q2: 连接立即关闭了？
**A**: 检查是否在发送消息之前就调用了 complete()，或者超时时间设置得太短。

### Q3: JSON 序列化失败？
**A**: 检查 Notification 和 SystemStatus 类是否有正确的 getter/setter 方法。

### Q4: 回调函数没有执行？
**A**: 确保在发送消息和关闭连接之前设置了回调函数。

### Q5: 超时测试不工作？
**A**: 确保超时时间设置正确，等待时间要超过超时时间。

---

## 📚 参考资源

1. **基础学习**：`SseEmitterLearningTest.java`
2. **实际应用**：`SseLearningController.java`
3. **学习指南**：`SSE_LEARNING_GUIDE.md`

---

## 🎉 完成测试后

完成所有测试用例后，你可以：

1. **查看参考答案**：对比自己的实现
2. **优化代码**：尝试更优雅的实现方式
3. **扩展功能**：添加自己的测试用例
4. **应用到实际项目**：在实际项目中使用 SSE

---

**祝你测试顺利！** 🚀

