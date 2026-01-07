# Controller 注解学习使用指南

## 📚 简介

`ControllerAnnotationsLearningController` 是一个专门用于学习 Spring MVC Controller 注解的示例控制器。通过实际运行和测试这个 Controller，你可以直观地理解每个注解的作用和用法。

## 🚀 快速开始

### 1. 启动应用

```bash
cd chat-service
mvn spring-boot:run
```

应用启动后，默认运行在 `http://localhost:6001`

### 2. 测试接口

你可以使用以下工具测试接口：
- **Postman**（推荐）
- **浏览器**（仅限 GET 请求）
- **curl** 命令
- **IntelliJ IDEA 的 HTTP Client**

## 📖 学习路径

### 第一部分：@GetMapping 注解学习

#### 示例 1：最简单的 GET 请求
```http
GET http://localhost:6001/api/learn/annotations/hello
```
**学习要点**：
- `@GetMapping` 将方法映射到 GET 请求
- 返回值自动转换为 JSON（因为类上有 `@RestController`）

#### 示例 2：@PathVariable 获取路径变量
```http
GET http://localhost:6001/api/learn/annotations/user/123
```
**学习要点**：
- `@PathVariable` 从 URL 路径中提取变量
- 路径中的 `{userId}` 对应方法参数 `userId`

#### 示例 3：多个路径变量
```http
GET http://localhost:6001/api/learn/annotations/user/123/order/456
```
**学习要点**：
- 一个方法可以有多个 `@PathVariable`
- 变量名必须与路径中的 `{变量名}` 一致

#### 示例 4：@RequestParam 获取查询参数
```http
GET http://localhost:6001/api/learn/annotations/search?keyword=Java&page=1&size=20
```
**学习要点**：
- `@RequestParam` 从 URL 查询参数获取值
- `required = false` 表示参数可选
- `defaultValue` 设置默认值

#### 示例 5：produces 指定响应类型
```http
GET http://localhost:6001/api/learn/annotations/json
```
**学习要点**：
- `produces` 指定响应内容的类型
- 查看响应头 `Content-Type: application/json`

### 第二部分：@PostMapping 注解学习

#### 示例 6：POST 请求基础用法
```http
POST http://localhost:6001/api/learn/annotations/user
Content-Type: application/x-www-form-urlencoded

name=张三&age=25
```
**学习要点**：
- `@PostMapping` 将方法映射到 POST 请求
- 使用 `@RequestParam` 获取表单参数

#### 示例 7：@RequestBody 接收 JSON
```http
POST http://localhost:6001/api/learn/annotations/user/json
Content-Type: application/json

{
  "name": "李四",
  "age": 30,
  "email": "lisi@example.com"
}
```
**学习要点**：
- `@RequestBody` 将 JSON 自动转换为 Java 对象
- `consumes` 指定只接受 JSON 格式的请求

#### 示例 8：混合使用多种注解
```http
POST http://localhost:6001/api/learn/annotations/user/123/order?productId=456&quantity=2
```
**学习要点**：
- 同时使用 `@PathVariable` 和 `@RequestParam`
- 路径变量和查询参数可以同时使用

### 第三部分：其他 HTTP 方法

#### 示例 9：@PutMapping 完整更新
```http
PUT http://localhost:6001/api/learn/annotations/user/123
Content-Type: application/json

{
  "name": "王五",
  "age": 28
}
```

#### 示例 10：@PatchMapping 部分更新
```http
PATCH http://localhost:6001/api/learn/annotations/user/123
Content-Type: application/json

{
  "age": 30
}
```

#### 示例 11：@DeleteMapping 删除资源
```http
DELETE http://localhost:6001/api/learn/annotations/user/123
```

### 第四部分：请求头和 Cookie

#### 示例 12：@RequestHeader 获取请求头
```http
GET http://localhost:6001/api/learn/annotations/header
Authorization: Bearer token123
User-Agent: MyApp/1.0
```

#### 示例 13：@CookieValue 获取 Cookie
```http
GET http://localhost:6001/api/learn/annotations/cookie
Cookie: sessionId=abc123; theme=dark
```

### 第五部分：响应控制

#### 示例 14：ResponseEntity 自定义响应
```http
GET http://localhost:6001/api/learn/annotations/response
```
**学习要点**：
- 查看响应头中的 `X-Custom-Header`
- `ResponseEntity` 可以自定义状态码和响应头

#### 示例 15：返回不同状态码
```http
GET http://localhost:6001/api/learn/annotations/status/200
GET http://localhost:6001/api/learn/annotations/status/404
GET http://localhost:6001/api/learn/annotations/status/500
```

### 第六部分：综合示例

#### 示例 16：综合使用多种注解
```http
POST http://localhost:6001/api/learn/annotations/complex/user/123?source=web
Content-Type: application/json
Authorization: Bearer token123

{
  "name": "综合测试",
  "age": 25
}
```
**学习要点**：
- 同时使用 `@PathVariable`、`@RequestParam`、`@RequestBody`、`@RequestHeader`
- 理解不同注解获取参数的方式

#### 示例 17：错误处理
```http
POST http://localhost:6001/api/learn/annotations/validate
Content-Type: application/json

{
  "name": "",
  "age": -5
}
```
**学习要点**：
- 参数验证
- 返回错误状态码和错误信息

#### 示例 18：注解速查表
```http
GET http://localhost:6001/api/learn/annotations/cheatsheet
```
**学习要点**：
- 查看所有注解的说明
- 作为学习参考

## 🛠️ 使用 Postman 测试

### 创建 Postman Collection

1. 打开 Postman
2. 创建新的 Collection：`Controller 注解学习`
3. 按照上面的示例创建请求

### 示例：创建第一个请求

1. 点击 "New" → "HTTP Request"
2. 方法选择：`GET`
3. URL 输入：`http://localhost:6001/api/learn/annotations/hello`
4. 点击 "Send"
5. 查看响应结果

## 🧪 使用 IntelliJ IDEA HTTP Client

在项目根目录创建 `test-requests.http` 文件：

```http
### 示例 1：最简单的 GET 请求
GET http://localhost:6001/api/learn/annotations/hello

### 示例 2：@PathVariable
GET http://localhost:6001/api/learn/annotations/user/123

### 示例 3：多个路径变量
GET http://localhost:6001/api/learn/annotations/user/123/order/456

### 示例 4：@RequestParam
GET http://localhost:6001/api/learn/annotations/search?keyword=Java&page=1&size=20

### 示例 5：produces
GET http://localhost:6001/api/learn/annotations/json

### 示例 6：POST 请求
POST http://localhost:6001/api/learn/annotations/user
Content-Type: application/x-www-form-urlencoded

name=张三&age=25

### 示例 7：@RequestBody
POST http://localhost:6001/api/learn/annotations/user/json
Content-Type: application/json

{
  "name": "李四",
  "age": 30,
  "email": "lisi@example.com"
}

### 示例 8：混合使用
POST http://localhost:6001/api/learn/annotations/user/123/order?productId=456&quantity=2

### 示例 9：PUT 请求
PUT http://localhost:6001/api/learn/annotations/user/123
Content-Type: application/json

{
  "name": "王五",
  "age": 28
}

### 示例 10：PATCH 请求
PATCH http://localhost:6001/api/learn/annotations/user/123
Content-Type: application/json

{
  "age": 30
}

### 示例 11：DELETE 请求
DELETE http://localhost:6001/api/learn/annotations/user/123

### 示例 12：@RequestHeader
GET http://localhost:6001/api/learn/annotations/header
Authorization: Bearer token123
User-Agent: MyApp/1.0

### 示例 13：@CookieValue
GET http://localhost:6001/api/learn/annotations/cookie
Cookie: sessionId=abc123; theme=dark

### 示例 14：ResponseEntity
GET http://localhost:6001/api/learn/annotations/response

### 示例 15：不同状态码
GET http://localhost:6001/api/learn/annotations/status/200
GET http://localhost:6001/api/learn/annotations/status/404
GET http://localhost:6001/api/learn/annotations/status/500

### 示例 16：综合示例
POST http://localhost:6001/api/learn/annotations/complex/user/123?source=web
Content-Type: application/json
Authorization: Bearer token123

{
  "name": "综合测试",
  "age": 25
}

### 示例 17：错误处理
POST http://localhost:6001/api/learn/annotations/validate
Content-Type: application/json

{
  "name": "",
  "age": -5
}

### 示例 18：注解速查表
GET http://localhost:6001/api/learn/annotations/cheatsheet
```

## 📝 学习建议

### 1. 按顺序学习
按照示例 1-18 的顺序，逐步理解每个注解的作用。

### 2. 观察响应
- 查看响应状态码
- 查看响应头（特别是 `Content-Type`）
- 查看响应体内容

### 3. 修改参数
尝试修改请求参数，观察不同的结果：
- 修改路径变量
- 修改查询参数
- 修改请求体
- 添加或删除请求头

### 4. 查看日志
观察控制台日志输出，理解请求处理过程。

### 5. 阅读代码
对照 `ControllerAnnotationsLearningController.java` 中的代码，理解每个注解的实际用法。

## 🔍 常见问题

### Q1：为什么返回值自动变成 JSON？
**A：** 因为类上有 `@RestController` 注解，它等同于 `@Controller + @ResponseBody`，返回值会自动序列化为 JSON。

### Q2：@RequestParam 和 @PathVariable 的区别？
**A：**
- `@RequestParam`：从查询参数获取，如 `/user?id=123`
- `@PathVariable`：从路径获取，如 `/user/123`

### Q3：什么时候用 @RequestBody？
**A：** 当需要接收复杂的 JSON 对象时，使用 `@RequestBody` 可以自动将 JSON 转换为 Java 对象。

### Q4：如何自定义响应状态码？
**A：** 使用 `ResponseEntity`，可以指定状态码、响应头和响应体。

### Q5：produces 和 consumes 的区别？
**A：**
- `produces`：指定响应内容的类型（告诉客户端返回什么格式）
- `consumes`：指定请求内容的类型（限制只接受什么格式）

## 📚 下一步学习

完成本指南的学习后，你可以：

1. **阅读源码**：查看 `ChatController.java`、`StreamController.java` 等实际项目中的 Controller
2. **编写自己的 Controller**：尝试创建一个新的 Controller，使用学到的注解
3. **学习参数验证**：了解 `@Valid`、`@NotNull` 等验证注解
4. **学习异常处理**：了解 `@ExceptionHandler`、`@ControllerAdvice` 等
5. **学习拦截器**：了解 `HandlerInterceptor` 的使用

## 🎉 总结

通过这个学习 Controller，你应该已经掌握了：

- ✅ 类级别注解：`@RestController`、`@RequestMapping`、`@Slf4j`
- ✅ HTTP 方法注解：`@GetMapping`、`@PostMapping`、`@PutMapping`、`@PatchMapping`、`@DeleteMapping`
- ✅ 参数绑定注解：`@RequestParam`、`@PathVariable`、`@RequestBody`、`@RequestHeader`、`@CookieValue`
- ✅ 响应控制：`produces`、`consumes`、`ResponseEntity`

继续实践，加深理解！🚀

