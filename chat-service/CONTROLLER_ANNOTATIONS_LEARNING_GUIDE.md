# Controller 注解学习指南

## 📚 简介

本指南将帮助你系统地学习 Spring MVC Controller 中的各种注解。通过完成 `ControllerAnnotationsLearningTest.java` 中的测试用例，你将掌握 Spring MVC 的核心注解用法。

## 🎯 学习目标

完成本指南后，你将能够：

1. ✅ 理解并正确使用类级别注解（@RestController、@RequestMapping）
2. ✅ 理解并正确使用方法级别注解（@GetMapping、@PostMapping 等）
3. ✅ 理解并正确使用参数绑定注解（@RequestParam、@PathVariable、@RequestBody）
4. ✅ 理解并正确使用响应相关属性（produces、consumes）
5. ✅ 掌握使用 MockMvc 进行 Controller 测试的方法

## 📖 注解详解

### 1. 类级别注解

#### @RestController
- **作用**：组合注解，等同于 `@Controller + @ResponseBody`
- **效果**：类中所有方法的返回值都会自动序列化为 JSON（或其他格式）返回给客户端
- **使用场景**：RESTful API 控制器

```java
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    // 所有方法的返回值都会自动转换为 JSON
}
```

#### @RequestMapping
- **作用**：在类上使用时，为所有方法添加统一的前缀路径
- **效果**：简化路径配置，避免重复
- **使用场景**：需要为控制器下所有接口添加统一前缀

```java
@RequestMapping("/api/chat")  // 类级别
public class ChatController {
    
    @GetMapping("/test")  // 方法级别
    // 最终路径：/api/chat/test
}
```

### 2. HTTP 方法注解

#### @GetMapping
- **作用**：将方法映射到 GET 请求
- **等同于**：`@RequestMapping(method = RequestMethod.GET)`
- **使用场景**：查询数据，不改变服务器状态

```java
@GetMapping("/test")
public String test() {
    return "Hello";
}
```

#### @PostMapping
- **作用**：将方法映射到 POST 请求
- **等同于**：`@RequestMapping(method = RequestMethod.POST)`
- **使用场景**：创建新资源

```java
@PostMapping("/submit")
public Map<String, Object> submit(@RequestParam String data) {
    // 处理提交逻辑
}
```

#### 其他 HTTP 方法注解
- `@PutMapping`：PUT 请求（完整更新资源）
- `@PatchMapping`：PATCH 请求（部分更新资源）
- `@DeleteMapping`：DELETE 请求（删除资源）

### 3. 参数绑定注解

#### @RequestParam
- **作用**：从请求中获取参数值（URL 参数、表单参数）
- **属性**：
  - `required`：参数是否必需（默认 true）
  - `defaultValue`：参数不存在时的默认值
- **使用场景**：获取查询参数、表单参数

```java
@PostMapping("/submit")
public String submit(
    @RequestParam String userId,  // 必需参数
    @RequestParam(required = false) String sessionId,  // 可选参数
    @RequestParam(defaultValue = "default") String type  // 带默认值
) {
    // ...
}
```

**请求示例**：
```
POST /api/chat/submit?userId=user123&question=你好&sessionId=session001
```

#### @PathVariable
- **作用**：从 URL 路径中获取变量值
- **使用场景**：RESTful API 设计，路径中包含资源 ID

```java
@GetMapping("/status/{requestId}")
public Map<String, Object> getStatus(@PathVariable String requestId) {
    // requestId 从 URL 路径中获取
}
```

**请求示例**：
```
GET /api/stream/status/abc123
// requestId = "abc123"
```

#### @RequestBody
- **作用**：将请求体（JSON、XML 等）转换为 Java 对象
- **使用场景**：接收 POST/PUT 请求的 JSON 数据

```java
@PostMapping("/user")
public User createUser(@RequestBody User user) {
    // user 对象从请求体 JSON 自动转换而来
    return userService.create(user);
}
```

**请求示例**：
```json
POST /api/user
Content-Type: application/json

{
  "name": "张三",
  "age": 25
}
```

#### @RequestHeader
- **作用**：从请求头中获取值
- **使用场景**：获取 Authorization、User-Agent 等请求头

```java
@GetMapping("/data")
public String getData(@RequestHeader("Authorization") String token) {
    // 从请求头中获取 Authorization 的值
}
```

### 4. 响应相关属性

#### produces
- **作用**：指定响应内容的类型（Content-Type）
- **使用场景**：指定返回 JSON、XML、文本、SSE 等格式

```java
@GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
public Map<String, Object> getData() {
    // 返回 JSON 格式
}

@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public SseEmitter stream() {
    // 返回 SSE 流式数据
}
```

#### consumes
- **作用**：指定请求内容的类型（Content-Type）
- **使用场景**：限制只接受特定格式的请求

```java
@PostMapping(value = "/data", consumes = MediaType.APPLICATION_JSON_VALUE)
public String processData(@RequestBody Map<String, Object> data) {
    // 只接受 JSON 格式的请求
}
```

## 🧪 如何使用测试文件

### 1. 打开测试文件

打开 `chat-service/src/test/java/com/xidian/chatservice/ControllerAnnotationsLearningTest.java`

### 2. 运行测试

#### 方式一：运行单个测试方法
1. 在测试方法上右键
2. 选择 "Run 'test01_GetMapping基础用法()'"
3. 观察测试结果

#### 方式二：运行所有测试
1. 在测试类上右键
2. 选择 "Run 'ControllerAnnotationsLearningTest'"
3. 观察所有测试结果

#### 方式三：使用 Maven 命令
```bash
cd chat-service
mvn test -Dtest=ControllerAnnotationsLearningTest
```

### 3. 学习步骤

1. **阅读注释**：每个测试用例都有详细的注释说明
2. **理解代码**：查看测试代码，理解每个注解的用法
3. **运行测试**：执行测试，观察结果
4. **查看日志**：查看控制台输出，理解请求和响应过程
5. **修改测试**：尝试修改测试代码，观察不同的结果

### 4. 测试用例说明

#### 测试用例 1：@GetMapping 基础用法
- **学习内容**：GET 请求的基本用法
- **关键点**：使用 `mockMvc.perform(get(...))` 发送 GET 请求

#### 测试用例 2：@PostMapping 基础用法
- **学习内容**：POST 请求的基本用法
- **关键点**：使用 `param()` 方法添加请求参数

#### 测试用例 3：@RequestParam 注解学习
- **学习内容**：理解 `required` 属性的作用
- **关键点**：必需参数 vs 可选参数

#### 测试用例 4：@PathVariable 注解学习
- **学习内容**：从 URL 路径获取变量
- **关键点**：RESTful API 设计

#### 测试用例 5：@RequestMapping 类级别注解
- **学习内容**：类级别路径前缀的作用
- **关键点**：路径组合规则

#### 测试用例 6：@RequestBody 注解学习（扩展）
- **学习内容**：如何发送 JSON 请求体
- **关键点**：JSON 序列化

#### 测试用例 7：@RequestHeader 注解学习（扩展）
- **学习内容**：如何发送自定义请求头
- **关键点**：请求头的作用

#### 测试用例 8：produces 属性学习
- **学习内容**：指定响应内容类型
- **关键点**：不同内容类型的区别

#### 测试用例 9：HTTP 方法注解综合测试
- **学习内容**：不同 HTTP 方法的区别
- **关键点**：RESTful API 设计原则

#### 测试用例 10：综合应用测试
- **学习内容**：综合运用所学知识
- **关键点**：完整的请求-响应流程

#### 测试用例 11：总结测试
- **学习内容**：验证所有知识点
- **关键点**：复习和巩固

## 📝 实践练习

### 练习 1：创建一个新的 Controller

创建一个新的 Controller，包含以下接口：

```java
@RestController
@RequestMapping("/api/practice")
public class PracticeController {
    
    // 1. GET 接口：获取用户信息
    // 路径：/api/practice/user/{userId}
    // 返回：用户信息 JSON
    
    // 2. POST 接口：创建用户
    // 路径：/api/practice/user
    // 参数：name, age（使用 @RequestParam）
    // 返回：创建的用户信息
    
    // 3. PUT 接口：更新用户
    // 路径：/api/practice/user/{userId}
    // 参数：使用 @RequestBody 接收 JSON
    // 返回：更新后的用户信息
}
```

### 练习 2：编写对应的测试

为 PracticeController 编写测试用例，验证：
- GET 请求能正确获取用户信息
- POST 请求能正确创建用户
- PUT 请求能正确更新用户
- 参数验证（必需参数、可选参数）
- 错误处理（404、400 等）

## 🔍 常见问题

### Q1：@RequestParam 和 @PathVariable 的区别？

**A：**
- `@RequestParam`：从查询参数或表单参数中获取值
  - 示例：`/api/user?id=123` → `@RequestParam String id`
- `@PathVariable`：从 URL 路径中获取值
  - 示例：`/api/user/123` → `@PathVariable String id`

### Q2：什么时候使用 @RequestBody？

**A：**
- 当需要接收复杂的 JSON 对象时
- 当请求体包含多个字段时
- 当需要自动类型转换时

### Q3：@RestController 和 @Controller 的区别？

**A：**
- `@RestController` = `@Controller + @ResponseBody`
- `@RestController` 的方法返回值会自动序列化为 JSON
- `@Controller` 的方法返回值会被当作视图名称（用于返回 HTML 页面）

### Q4：如何测试 Controller？

**A：**
- 使用 `@SpringBootTest` 和 `@AutoConfigureMockMvc`
- 使用 `MockMvc` 模拟 HTTP 请求
- 使用 `andExpect()` 验证响应结果

## 📚 扩展学习

### 推荐阅读

1. **Spring MVC 官方文档**
   - https://docs.spring.io/spring-framework/reference/web/webmvc.html

2. **RESTful API 设计指南**
   - https://restfulapi.net/

3. **MockMvc 测试指南**
   - https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html

### 相关文件

- `ChatController.java` - 实际项目中的 Controller 示例
- `StreamController.java` - SSE 流式 Controller 示例
- `SseLearningController.java` - SSE 学习 Controller 示例

## ✅ 学习检查清单

完成以下任务后，说明你已经掌握了 Controller 注解：

- [ ] 能够理解 @RestController 的作用
- [ ] 能够理解 @RequestMapping 的类级别和方法级别用法
- [ ] 能够正确使用 @GetMapping、@PostMapping 等 HTTP 方法注解
- [ ] 能够正确使用 @RequestParam 获取请求参数
- [ ] 能够正确使用 @PathVariable 获取路径变量
- [ ] 能够理解 @RequestBody 的作用和使用场景
- [ ] 能够理解 produces 和 consumes 的作用
- [ ] 能够使用 MockMvc 编写 Controller 测试
- [ ] 能够理解 RESTful API 设计原则
- [ ] 能够独立创建一个完整的 Controller 并编写测试

## 🎉 恭喜！

完成所有测试用例后，你已经掌握了 Spring MVC Controller 的核心注解！

继续学习：
- Spring MVC 拦截器
- Spring MVC 异常处理
- Spring MVC 参数验证
- Spring Security 集成

祝你学习愉快！🚀

