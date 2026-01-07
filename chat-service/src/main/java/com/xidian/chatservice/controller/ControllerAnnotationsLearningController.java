package com.xidian.chatservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ============================================
 * 📚 Controller 注解学习控制器
 * ============================================
 * 
 * 这个 Controller 专门用于学习 Spring MVC 中的各种注解。
 * 每个方法都展示了不同注解的用法，并配有详细的注释说明。
 * 
 * 如何使用：
 * 1. 启动应用后，使用 Postman、浏览器或 curl 测试各个接口
 * 2. 观察每个注解的实际效果
 * 3. 修改代码，尝试不同的参数组合
 * 4. 查看日志输出，理解请求处理过程
 * 
 * ============================================
 * 📖 学习路径：
 * ============================================
 * 1. 先看类级别的注解（@RestController、@RequestMapping）
 * 2. 再看方法级别的注解（@GetMapping、@PostMapping 等）
 * 3. 然后看参数绑定注解（@RequestParam、@PathVariable 等）
 * 4. 最后看响应相关属性（produces、consumes）
 * 
 * ============================================
 */
@Slf4j  // Lombok 注解：自动生成 log 对象，可以直接使用 log.info() 等方法
@RestController  // 组合注解：等同于 @Controller + @ResponseBody，返回值自动转为 JSON
@RequestMapping("/api/learn/annotations")  // 类级别：为所有方法添加统一路径前缀
public class ControllerAnnotationsLearningController {
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    // ============================================
    // 第一部分：@GetMapping 注解学习
    // ============================================
    
    /**
     * 示例 1：最简单的 GET 请求
     * 
     * @GetMapping 作用：
     * - 将方法映射到 GET 请求
     * - 等同于 @RequestMapping(method = RequestMethod.GET)
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/hello
     * 
     * 预期结果：返回 "Hello, Spring MVC!"
     */
    @GetMapping("/hello")
    public String hello() {
        log.info("收到 GET 请求：/hello");
        return "Hello, Spring MVC!";
    }

    /**
     * 示例 2：@GetMapping 带路径变量
     * 
     * @PathVariable 作用：
     * - 从 URL 路径中获取变量值
     * - 变量名必须与路径中的 {变量名} 一致
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/user/123
     * 
     * 预期结果：返回 "用户ID: 123"
     */
    @GetMapping("/user/{userId}")           //取路径变量 要用花括号括起来；
    public String getUserById(@PathVariable Integer userId) {
        log.info("收到 GET 请求：/user/{}", userId);
        return "用户ID: " + userId;
    }

    /**
     * 示例 3：@GetMapping 带多个路径变量
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/user/123/order/456
     * 
     * 预期结果：返回包含用户ID和订单ID的JSON
     */
    @GetMapping("/user/{userId}/order/{orderId}")
    public String getUserOrder(
            @PathVariable String userId,
            @PathVariable String orderId) throws JsonProcessingException {
        log.info("收到 GET 请求：/user/{}/order/{}", userId, orderId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("orderId", orderId);
        result.put("message", "获取用户订单信息");

        String string = objectMapper.writeValueAsString(result);

        return string;
    }

    /**
     * 示例 4：@GetMapping 带查询参数
     * 
     * @RequestParam 作用：
     * - 从 URL 查询参数或表单参数中获取值
     * - required = true（默认）：参数必须存在
     * - required = false：参数可选
     * - defaultValue：参数不存在时的默认值
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/search?keyword=Java&page=1
     * GET http://localhost:6001/api/learn/annotations/search?keyword=Java
     * GET http://localhost:6001/api/learn/annotations/search?keyword=Java&page=2&size=20
     * 
     * 预期结果：返回包含搜索参数的JSON
     */
    @GetMapping("/search")
    public Map<String, Object> search(
            @RequestParam String keyword,  // 必需参数
            @RequestParam(required = false, defaultValue = "1") Integer page,  // 可选参数，默认值为1
            @RequestParam(required = false, defaultValue = "10") Integer size) {  // 可选参数，默认值为10
        
        log.info("收到 GET 请求：/search, keyword={}, page={}, size={}", keyword, page, size);
        
        Map<String, Object> result = new HashMap<>();
        result.put("keyword", keyword);
        result.put("page", page);
        result.put("size", size);
        result.put("message", "搜索功能");
        return result;
    }

    /**
     * 示例 5：@GetMapping 指定响应类型（produces）
     * 
     * produces 作用：
     * - 指定响应内容的类型（Content-Type）
     * - 告诉客户端返回的数据格式
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/json
     * 
     * 预期结果：返回 JSON 格式数据，Content-Type: application/json
     */
    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getJson() {
        log.info("收到 GET 请求：/json");
        
        Map<String, Object> result = new HashMap<>();
        result.put("name", "张三");
        result.put("age", 25);
        result.put("timestamp", LocalDateTime.now().toString());
        return result;
    }

    // ============================================
    // 第二部分：@PostMapping 注解学习
    // ============================================
    
    /**
     * 示例 6：@PostMapping 基础用法
     * 
     * @PostMapping 作用：
     * - 将方法映射到 POST 请求
     * - 等同于 @RequestMapping(method = RequestMethod.POST)
     * - 通常用于创建新资源
     * 
     * 测试方式：
     * POST http://localhost:6001/api/learn/annotations/user
     * Content-Type: application/x-www-form-urlencoded
     * Body: name=张三&age=25
     * 
     * 预期结果：返回创建的用户信息
     */
    @PostMapping("/user")
    public Map<String, Object> createUser(
            @RequestParam String name,
            @RequestParam Integer age) {
        log.info("收到 POST 请求：/user, name={}, age={}", name, age);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", System.currentTimeMillis());  // 模拟生成ID
        result.put("name", name);
        result.put("age", age);
        result.put("message", "用户创建成功");
        return result;
    }

    /**
     * 示例 7：@PostMapping 接收 JSON 请求体
     * 
     * @RequestBody 作用：
     * - 将请求体（JSON、XML 等）自动转换为 Java 对象
     * - 需要指定 Content-Type: application/json
     * 
     * 测试方式：
     * POST http://localhost:6001/api/learn/annotations/user/json
     * Content-Type: application/json
     * Body: {"name":"李四","age":30,"email":"lisi@example.com"}
     * 
     * 预期结果：返回创建的用户信息
     */
    @PostMapping(value = "/user/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> createUserWithJson(@RequestBody Map<String, Object> userData) {
        log.info("收到 POST 请求：/user/json, userData={}", userData);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", System.currentTimeMillis());
        result.putAll(userData);
        result.put("message", "用户创建成功（JSON方式）");
        return result;
    }

    /**
     * 示例 8：@PostMapping 混合使用 @RequestParam 和 @PathVariable
     * 
     * 测试方式：
     * POST http://localhost:6001/api/learn/annotations/user/123/order?productId=456&quantity=2
     * 
     * 预期结果：返回订单信息
     */
    @PostMapping("/user/{userId}/order")
    public Map<String, Object> createOrder(
            @PathVariable String userId,  // 从路径获取
            @RequestParam String productId,  // 从查询参数获取
            @RequestParam Integer quantity) {  // 从查询参数获取
        
        log.info("收到 POST 请求：/user/{}/order, productId={}, quantity={}", userId, productId, quantity);
        
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", System.currentTimeMillis());
        result.put("userId", userId);
        result.put("productId", productId);
        result.put("quantity", quantity);
        result.put("message", "订单创建成功");
        return result;
    }

    // ============================================
    // 第三部分：其他 HTTP 方法注解学习
    // ============================================
    
    /**
     * 示例 9：@PutMapping 完整更新资源
     * 
     * @PutMapping 作用：
     * - 将方法映射到 PUT 请求
     * - 通常用于完整更新资源（替换整个资源）
     * 
     * 测试方式：
     * PUT http://localhost:6001/api/learn/annotations/user/123
     * Content-Type: application/json
     * Body: {"name":"王五","age":28}
     * 
     * 预期结果：返回更新后的用户信息
     */
    @PutMapping("/user/{userId}")
    public Map<String, Object> updateUser(
            @PathVariable String userId,
            @RequestBody Map<String, Object> userData) {
        log.info("收到 PUT 请求：/user/{}, userData={}", userId, userData);
        
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.putAll(userData);
        result.put("message", "用户信息已完整更新");
        return result;
    }

    /**
     * 示例 10：@PatchMapping 部分更新资源
     * 
     * @PatchMapping 作用：
     * - 将方法映射到 PATCH 请求
     * - 通常用于部分更新资源（只更新部分字段）
     * 
     * 测试方式：
     * PATCH http://localhost:6001/api/learn/annotations/user/123
     * Content-Type: application/json
     * Body: {"age":30}
     * 
     * 预期结果：返回更新后的用户信息
     */
    @PatchMapping("/user/{userId}")
    public Map<String, Object> partialUpdateUser(
            @PathVariable String userId,
            @RequestBody Map<String, Object> updates) {
        log.info("收到 PATCH 请求：/user/{}, updates={}", userId, updates);
        
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.putAll(updates);
        result.put("message", "用户信息已部分更新");
        return result;
    }

    /**
     * 示例 11：@DeleteMapping 删除资源
     * 
     * @DeleteMapping 作用：
     * - 将方法映射到 DELETE 请求
     * - 通常用于删除资源
     * 
     * 测试方式：
     * DELETE http://localhost:6001/api/learn/annotations/user/123
     * 
     * 预期结果：返回删除成功信息
     */
    @DeleteMapping("/user/{userId}")
    public Map<String, Object> deleteUser(@PathVariable String userId) {
        log.info("收到 DELETE 请求：/user/{}", userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("message", "用户已删除");
        return result;
    }

    // ============================================
    // 第四部分：@RequestHeader 和 @CookieValue 学习
    // ============================================
    
    /**
     * 示例 12：@RequestHeader 获取请求头
     * 
     * @RequestHeader 作用：
     * - 从请求头中获取值
     * - 常用于获取 Authorization、User-Agent 等
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/header
     * Headers: Authorization: Bearer token123, User-Agent: MyApp/1.0
     * 
     * 预期结果：返回请求头信息
     */
    @GetMapping("/header")
    public Map<String, Object> getHeader(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("收到 GET 请求：/header, Authorization={}, User-Agent={}", authorization, userAgent);
        
        Map<String, Object> result = new HashMap<>();
        result.put("authorization", authorization);
        result.put("userAgent", userAgent);
        result.put("message", "获取请求头信息");
        return result;
    }

    /**
     * 示例 13：@CookieValue 获取 Cookie 值
     * 
     * @CookieValue 作用：
     * - 从 Cookie 中获取值
     * - 常用于获取会话信息、用户偏好等
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/cookie
     * Cookie: sessionId=abc123; theme=dark
     * 
     * 预期结果：返回 Cookie 信息
     */
    @GetMapping("/cookie")
    public Map<String, Object> getCookie(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @CookieValue(value = "theme", required = false, defaultValue = "light") String theme) {
        log.info("收到 GET 请求：/cookie, sessionId={}, theme={}", sessionId, theme);
        
        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", sessionId);
        result.put("theme", theme);
        result.put("message", "获取 Cookie 信息");
        return result;
    }

    // ============================================
    // 第五部分：ResponseEntity 和状态码学习
    // ============================================
    
    /**
     * 示例 14：使用 ResponseEntity 自定义响应
     * 
     * ResponseEntity 作用：
     * - 可以自定义 HTTP 状态码、响应头、响应体
     * - 比直接返回对象更灵活
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/response
     * 
     * 预期结果：返回自定义状态码和响应头
     */
    @GetMapping("/response")
    public ResponseEntity<Map<String, Object>> getResponse() {
        log.info("收到 GET 请求：/response");
        
        Map<String, Object> body = new HashMap<>();
        body.put("message", "自定义响应");
        body.put("timestamp", LocalDateTime.now().toString());
        
        // 返回 200 状态码，并设置自定义响应头
        return ResponseEntity.ok()
                .header("X-Custom-Header", "CustomValue")
                .body(body);
    }

    /**
     * 示例 15：返回不同的 HTTP 状态码
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/status/200
     * GET http://localhost:6001/api/learn/annotations/status/404
     * GET http://localhost:6001/api/learn/annotations/status/500
     * 
     * 预期结果：返回对应的 HTTP 状态码
     */
    @GetMapping("/status/{code}")
    public ResponseEntity<Map<String, Object>> getStatus(@PathVariable int code) {
        log.info("收到 GET 请求：/status/{}", code);
        
        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("message", "状态码: " + code);
        
        // 根据路径参数返回不同的状态码
        HttpStatus status = switch (code) {
            case 200 -> HttpStatus.OK;
            case 201 -> HttpStatus.CREATED;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 404 -> HttpStatus.NOT_FOUND;
            case 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.OK;
        };
        
        return ResponseEntity.status(status).body(body);
    }

    // ============================================
    // 第六部分：综合示例
    // ============================================
    
    /**
     * 示例 16：综合使用多种注解
     * 
     * 这个示例展示了如何在一个方法中同时使用多种注解：
     * - @PostMapping：指定 HTTP 方法
     * - @PathVariable：获取路径变量
     * - @RequestParam：获取查询参数
     * - @RequestBody：获取请求体
     * - @RequestHeader：获取请求头
     * - produces：指定响应类型
     * - consumes：指定请求类型
     * 
     * 测试方式：
     * POST http://localhost:6001/api/learn/annotations/complex/user/123?source=web
     * Content-Type: application/json
     * Authorization: Bearer token123
     * Body: {"name":"综合测试","age":25}
     * 
     * 预期结果：返回包含所有参数的信息
     */
    @PostMapping(
            value = "/complex/user/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> complexExample(
            @PathVariable String userId,  // 从路径获取
            @RequestParam(required = false, defaultValue = "unknown") String source,  // 从查询参数获取
            @RequestBody Map<String, Object> userData,  // 从请求体获取
            @RequestHeader(value = "Authorization", required = false) String auth) {  // 从请求头获取
        
        log.info("收到 POST 请求：/complex/user/{}, source={}, userData={}, auth={}", 
                userId, source, userData, auth);
        
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("source", source);
        result.put("userData", userData);
        result.put("hasAuth", auth != null);
        result.put("message", "综合示例：使用了多种注解");
        result.put("timestamp", LocalDateTime.now().toString());
        return result;
    }

    /**
     * 示例 17：错误处理示例
     * 
     * 这个示例展示了如何处理参数验证错误
     * 
     * 测试方式：
     * POST http://localhost:6001/api/learn/annotations/validate
     * Content-Type: application/json
     * Body: {"name":"测试","age":-5}  // age 为负数，应该返回错误
     * 
     * 预期结果：返回错误信息
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateUser(@RequestBody Map<String, Object> userData) {
        log.info("收到 POST 请求：/validate, userData={}", userData);
        
        String name = (String) userData.get("name");
        Integer age = (Integer) userData.get("age");
        
        // 简单的参数验证
        if (name == null || name.trim().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "姓名不能为空");
            return ResponseEntity.badRequest().body(error);
        }
        
        if (age == null || age < 0) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "年龄必须大于等于0");
            return ResponseEntity.badRequest().body(error);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("message", "验证通过");
        result.put("userData", userData);
        return ResponseEntity.ok(result);
    }

    // ============================================
    // 总结：注解速查表
    // ============================================
    
    /**
     * 示例 18：注解速查表接口
     * 
     * 返回所有注解的说明和使用示例
     * 
     * 测试方式：
     * GET http://localhost:6001/api/learn/annotations/cheatsheet
     * 
     * 预期结果：返回注解说明文档
     */
    @GetMapping("/cheatsheet")
    public Map<String, Object> getCheatsheet() {
        Map<String, Object> cheatsheet = new HashMap<>();
        
        // 类级别注解
        Map<String, String> classAnnotations = new HashMap<>();
        classAnnotations.put("@RestController", "组合注解：@Controller + @ResponseBody，返回值自动转为JSON");
        classAnnotations.put("@RequestMapping", "类级别：为所有方法添加统一路径前缀");
        classAnnotations.put("@Slf4j", "Lombok注解：自动生成log对象");
        cheatsheet.put("类级别注解", classAnnotations);
        
        // HTTP方法注解
        Map<String, String> methodAnnotations = new HashMap<>();
        methodAnnotations.put("@GetMapping", "GET请求：查询数据，不改变服务器状态");
        methodAnnotations.put("@PostMapping", "POST请求：创建新资源");
        methodAnnotations.put("@PutMapping", "PUT请求：完整更新资源");
        methodAnnotations.put("@PatchMapping", "PATCH请求：部分更新资源");
        methodAnnotations.put("@DeleteMapping", "DELETE请求：删除资源");
        cheatsheet.put("HTTP方法注解", methodAnnotations);
        
        // 参数绑定注解
        Map<String, String> paramAnnotations = new HashMap<>();
        paramAnnotations.put("@RequestParam", "获取请求参数：URL参数、表单参数");
        paramAnnotations.put("@PathVariable", "获取路径变量：从URL路径中提取");
        paramAnnotations.put("@RequestBody", "获取请求体：JSON、XML等自动转换为对象");
        paramAnnotations.put("@RequestHeader", "获取请求头：Authorization、User-Agent等");
        paramAnnotations.put("@CookieValue", "获取Cookie值：sessionId、theme等");
        cheatsheet.put("参数绑定注解", paramAnnotations);
        
        // 响应相关
        Map<String, String> responseAttributes = new HashMap<>();
        responseAttributes.put("produces", "指定响应内容类型：application/json、text/plain等");
        responseAttributes.put("consumes", "指定请求内容类型：限制只接受特定格式");
        responseAttributes.put("ResponseEntity", "自定义响应：状态码、响应头、响应体");
        cheatsheet.put("响应相关", responseAttributes);
        
        cheatsheet.put("说明", "使用 Postman 或 curl 测试各个接口，观察每个注解的实际效果");
        
        return cheatsheet;
    }
}

