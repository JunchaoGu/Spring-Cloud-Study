package com.xidian.chatservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ============================================
 * 📚 Controller 注解学习测试类
 * ============================================
 * 
 * 本测试类将帮助你系统地学习 Spring MVC Controller 中的各种注解。
 * 通过完成这些测试用例，你将掌握：
 * 
 * 1. 类级别注解：
 *    - @RestController：组合注解，等同于 @Controller + @ResponseBody
 *    - @RequestMapping：映射请求路径
 *    - @Slf4j：Lombok 日志注解
 * 
 * 2. 方法级别注解：
 *    - @GetMapping：处理 GET 请求
 *    - @PostMapping：处理 POST 请求
 *    - @PutMapping：处理 PUT 请求
 *    - @DeleteMapping：处理 DELETE 请求
 *    - @PatchMapping：处理 PATCH 请求
 * 
 * 3. 参数绑定注解：
 *    - @RequestParam：获取请求参数（URL 参数、表单参数）
 *    - @PathVariable：获取路径变量
 *    - @RequestBody：获取请求体（JSON、XML 等）
 *    - @RequestHeader：获取请求头
 *    - @CookieValue：获取 Cookie 值
 * 
 * 4. 响应相关：
 *    - produces：指定响应内容类型
 *    - consumes：指定请求内容类型
 * 
 * ============================================
 * 🎯 学习目标：
 * ============================================
 * - 理解每个注解的作用和使用场景
 * - 掌握如何编写 RESTful API
 * - 学会使用 MockMvc 进行 Controller 测试
 * - 理解请求参数的不同绑定方式
 * 
 * ============================================
 * 📖 使用说明：
 * ============================================
 * 1. 每个测试用例都有详细的注释说明
 * 2. 按照注释中的 TODO 提示完成测试
 * 3. 运行测试并观察结果
 * 4. 理解每个注解的实际效果
 * 
 * ============================================
 * 💡 提示：
 * ============================================
 * - 使用 MockMvc 模拟 HTTP 请求
 * - 使用 andExpect() 验证响应结果
 * - 使用 andDo(print()) 打印请求和响应详情
 * - 注意不同注解的参数获取方式
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc  // 自动配置 MockMvc，用于模拟 HTTP 请求
public class ControllerAnnotationsLearningTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc：模拟 HTTP 请求的工具

    @Autowired
    private ObjectMapper objectMapper;  // ObjectMapper：JSON 序列化/反序列化工具

    @BeforeEach
    void setUp() {
        log.info("============================================");
        log.info("开始 Controller 注解学习测试");
        log.info("============================================");
    }

    // ============================================
    // 测试用例 1：@GetMapping 基础用法
    // ============================================
    /**
     * 学习目标：理解 @GetMapping 注解
     * 
     * @GetMapping 作用：
     * - 将方法映射到 GET 请求
     * - 等同于 @RequestMapping(method = RequestMethod.GET)
     * 
     * 测试接口：GET /api/chat/test
     * 预期结果：返回 "Chat Service is running! "
     */
    @Test
    public void test01_GetMapping基础用法() throws Exception {
        log.info("========== 测试用例1：@GetMapping 基础用法 ==========");
        
        // TODO: 使用 MockMvc 发送 GET 请求到 /api/chat/test
        // 提示：
        // 1. 使用 mockMvc.perform(get("/api/chat/test"))
        // 2. 使用 andExpect(status().isOk()) 验证状态码为 200
        // 3. 使用 andExpect(content().string("Chat Service is running! ")) 验证响应内容
        // 4. 使用 andDo(print()) 打印请求和响应详情
        
        // ========== 开始编写代码 ==========
        
        mockMvc.perform(get("/api/chat/test"))
                .andExpect(status().isOk())  // 验证 HTTP 状态码为 200
                .andExpect(content().string("Chat Service is running! "))  // 验证响应内容
                .andDo(print());  // 打印请求和响应详情
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例1完成：@GetMapping 成功处理 GET 请求");
    }

    // ============================================
    // 测试用例 2：@PostMapping 基础用法
    // ============================================
    /**
     * 学习目标：理解 @PostMapping 注解
     * 
     * @PostMapping 作用：
     * - 将方法映射到 POST 请求
     * - 等同于 @RequestMapping(method = RequestMethod.POST)
     * 
     * 测试接口：POST /api/chat/submit
     * 请求参数：userId=user123&question=你好&sessionId=session001
     * 预期结果：返回包含 requestId 的 JSON
     */
    @Test
    public void test02_PostMapping基础用法() throws Exception {
        log.info("========== 测试用例2：@PostMapping 基础用法 ==========");
        
        // TODO: 使用 MockMvc 发送 POST 请求到 /api/chat/submit
        // 提示：
        // 1. 使用 mockMvc.perform(post("/api/chat/submit"))
        // 2. 使用 param("userId", "user123") 添加请求参数
        // 3. 使用 param("question", "你好") 添加请求参数
        // 4. 使用 param("sessionId", "session001") 添加请求参数（可选）
        // 5. 验证状态码为 200
        // 6. 验证响应是 JSON 格式
        // 7. 验证响应包含 "requestId" 字段
        
        // ========== 开始编写代码 ==========
        
        mockMvc.perform(post("/api/chat/submit")
                        .param("userId", "user123")
                        .param("question", "你好")
                        .param("sessionId", "session001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requestId").exists())  // 验证 JSON 中包含 requestId 字段
                .andExpect(jsonPath("$.status").value("queued"))  // 验证 status 字段值为 "queued"
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例2完成：@PostMapping 成功处理 POST 请求");
    }

    // ============================================
    // 测试用例 3：@RequestParam 注解学习
    // ============================================
    /**
     * 学习目标：理解 @RequestParam 注解
     * 
     * @RequestParam 作用：
     * - 从请求中获取参数值（URL 参数、表单参数）
     * - 可以设置 required、defaultValue 等属性
     * 
     * 属性说明：
     * - required = true（默认）：参数必须存在，否则抛出异常
     * - required = false：参数可选
     * - defaultValue = "默认值"：参数不存在时使用默认值
     * 
     * 测试场景：
     * 1. 测试必需参数（userId, question）
     * 2. 测试可选参数（sessionId）
     * 3. 测试缺少必需参数时的错误处理
     */
    @Test
    public void test03_RequestParam注解学习() throws Exception {
        log.info("========== 测试用例3：@RequestParam 注解学习 ==========");
        
        // TODO: 完成以下三个测试场景
        
        // 场景1：测试所有参数都存在的情况
        log.info("--- 场景1：所有参数都存在 ---");
        mockMvc.perform(post("/api/chat/submit")
                        .param("userId", "user123")
                        .param("question", "测试问题")
                        .param("sessionId", "session001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").exists())
                .andDo(print());
        
        // 场景2：测试可选参数不存在的情况（sessionId 是可选的）
        log.info("--- 场景2：可选参数不存在 ---");
        mockMvc.perform(post("/api/chat/submit")
                        .param("userId", "user123")
                        .param("question", "测试问题"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").exists())
                .andDo(print());
        
        // 场景3：测试缺少必需参数的情况（应该返回 400 错误）
        log.info("--- 场景3：缺少必需参数 ---");
        mockMvc.perform(post("/api/chat/submit")
                        .param("userId", "user123"))
                // TODO: 验证状态码为 400（Bad Request）
                // 提示：使用 andExpect(status().isBadRequest())
                .andExpect(status().isBadRequest())
                .andDo(print());
        
        log.info("✅ 测试用例3完成：理解了 @RequestParam 的 required 属性");
    }

    // ============================================
    // 测试用例 4：@PathVariable 注解学习
    // ============================================
    /**
     * 学习目标：理解 @PathVariable 注解
     * 
     * @PathVariable 作用：
     * - 从 URL 路径中获取变量值
     * - 常用于 RESTful API 设计
     * 
     * 示例：
     * - URL: /api/stream/{requestId}
     * - 方法参数: @PathVariable String requestId
     * - 当访问 /api/stream/abc123 时，requestId = "abc123"
     * 
     * 测试接口：GET /api/stream/status/{requestId}
     * 预期结果：返回包含 requestId 的状态信息
     */
    @Test
    public void test04_PathVariable注解学习() throws Exception {
        log.info("========== 测试用例4：@PathVariable 注解学习 ==========");
        
        String testRequestId = "test-request-123";
        
        // TODO: 使用 MockMvc 发送 GET 请求，路径中包含变量
        // 提示：
        // 1. 使用 get("/api/stream/status/{requestId}", testRequestId)
        // 2. 验证状态码为 200
        // 3. 验证响应 JSON 中包含 requestId 字段，且值等于 testRequestId
        // 4. 验证 connected 字段存在
        
        // ========== 开始编写代码 ==========
        
        mockMvc.perform(get("/api/stream/status/{requestId}", testRequestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(testRequestId))
                .andExpect(jsonPath("$.connected").exists())
                .andExpect(jsonPath("$.totalConnections").exists())
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例4完成：理解了 @PathVariable 从 URL 路径获取变量");
    }

    // ============================================
    // 测试用例 5：@RequestMapping 类级别注解
    // ============================================
    /**
     * 学习目标：理解 @RequestMapping 类级别注解
     * 
     * @RequestMapping 作用：
     * - 在类上使用：为所有方法添加统一的前缀路径
     * - 在方法上使用：指定具体的路径和请求方法
     * 
     * 示例：
     * - 类上：@RequestMapping("/api/chat")
     * - 方法上：@GetMapping("/test")
     * - 最终路径：/api/chat/test
     * 
     * 测试：验证类级别的路径前缀是否正确
     */
    @Test
    public void test05_RequestMapping类级别注解() throws Exception {
        log.info("========== 测试用例5：@RequestMapping 类级别注解 ==========");
        
        // TODO: 验证类级别的 @RequestMapping 路径前缀
        // 提示：
        // 1. ChatController 类上有 @RequestMapping("/api/chat")
        // 2. test() 方法上有 @GetMapping("/test")
        // 3. 完整路径应该是 /api/chat/test
        
        // ========== 开始编写代码 ==========
        
        // 测试路径：/api/chat/test（类级别路径 + 方法路径）
        mockMvc.perform(get("/api/chat/test"))
                .andExpect(status().isOk())
                .andDo(print());
        
        // 测试路径：/api/chat/submit（类级别路径 + 方法路径）
        mockMvc.perform(post("/api/chat/submit")
                        .param("userId", "user123")
                        .param("question", "测试"))
                .andExpect(status().isOk())
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例5完成：理解了类级别的 @RequestMapping 路径前缀");
    }

    // ============================================
    // 测试用例 6：@RequestBody 注解学习（扩展）
    // ============================================
    /**
     * 学习目标：理解 @RequestBody 注解
     * 
     * @RequestBody 作用：
     * - 将请求体（JSON、XML 等）转换为 Java 对象
     * - 常用于接收 POST/PUT 请求的 JSON 数据
     * 
     * 注意：虽然当前 Controller 中没有使用 @RequestBody，
     * 但这是非常重要的注解，值得学习。
     * 
     * 示例用法：
     * @PostMapping("/user")
     * public User createUser(@RequestBody User user) {
     *     // user 对象从请求体 JSON 自动转换而来
     * }
     * 
     * 本测试用例演示如何发送 JSON 请求体（虽然当前接口不支持）
     */
    @Test
    public void test06_RequestBody注解学习() throws Exception {
        log.info("========== 测试用例6：@RequestBody 注解学习（扩展） ==========");
        
        // 创建一个 JSON 对象
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", "user123");
        requestBody.put("question", "测试问题");
        requestBody.put("sessionId", "session001");
        
        // TODO: 学习如何发送 JSON 请求体
        // 提示：
        // 1. 使用 content() 方法设置请求体内容
        // 2. 使用 contentType(MediaType.APPLICATION_JSON) 设置内容类型
        // 3. 使用 objectMapper.writeValueAsString() 将对象转换为 JSON 字符串
        
        // ========== 开始编写代码 ==========
        
        // 注意：当前 ChatController 的 submitChat 方法使用的是 @RequestParam，
        // 所以这个请求会失败。这里只是演示如何发送 JSON 请求体。
        mockMvc.perform(post("/api/chat/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例6完成：学习了如何发送 JSON 请求体（@RequestBody 用法）");
        log.info("💡 提示：当前接口使用 @RequestParam，所以这个请求会失败，这是正常的");
    }

    // ============================================
    // 测试用例 7：@RequestHeader 注解学习（扩展）
    // ============================================
    /**
     * 学习目标：理解 @RequestHeader 注解
     * 
     * @RequestHeader 作用：
     * - 从请求头中获取值
     * - 常用于获取 Authorization、User-Agent 等请求头
     * 
     * 示例用法：
     * public String getData(@RequestHeader("Authorization") String token) {
     *     // 从请求头中获取 Authorization 的值
     * }
     * 
     * 本测试用例演示如何发送自定义请求头
     */
    @Test
    public void test07_RequestHeader注解学习() throws Exception {
        log.info("========== 测试用例7：@RequestHeader 注解学习（扩展） ==========");
        
        // TODO: 学习如何发送自定义请求头
        // 提示：
        // 1. 使用 header("HeaderName", "HeaderValue") 添加请求头
        // 2. 可以添加多个请求头
        
        // ========== 开始编写代码 ==========
        
        mockMvc.perform(get("/api/chat/test")
                        .header("Authorization", "Bearer token123")
                        .header("User-Agent", "Test-Agent")
                        .header("X-Custom-Header", "CustomValue"))
                .andExpect(status().isOk())
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例7完成：学习了如何发送自定义请求头（@RequestHeader 用法）");
    }

    // ============================================
    // 测试用例 8：produces 属性学习
    // ============================================
    /**
     * 学习目标：理解 produces 属性
     * 
     * produces 作用：
     * - 指定响应内容的类型（Content-Type）
     * - 常用于指定返回 JSON、XML、文本等格式
     * 
     * 示例：
     * @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
     * 
     * 测试接口：GET /api/learn/sse/simple
     * 该接口使用 produces = MediaType.TEXT_EVENT_STREAM_VALUE
     * 用于 SSE（Server-Sent Events）流式响应
     */
    @Test
    public void test08_Produces属性学习() throws Exception {
        log.info("========== 测试用例8：produces 属性学习 ==========");
        
        // TODO: 测试不同 produces 类型的接口
        // 提示：
        // 1. 测试普通 JSON 接口（/api/chat/test）
        // 2. 测试 SSE 流式接口（/api/learn/sse/simple）
        // 3. 验证响应内容类型
        
        // ========== 开始编写代码 ==========
        
        // 场景1：测试普通接口（默认返回 JSON 或文本）
        log.info("--- 场景1：普通接口 ---");
        mockMvc.perform(get("/api/chat/test"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))  // 文本类型
                .andDo(print());
        
        // 场景2：测试 SSE 流式接口（返回 text/event-stream）
        log.info("--- 场景2：SSE 流式接口 ---");
        mockMvc.perform(get("/api/learn/sse/simple"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_EVENT_STREAM))  // SSE 类型
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例8完成：理解了 produces 属性指定响应内容类型");
    }

    // ============================================
    // 测试用例 9：HTTP 方法注解综合测试
    // ============================================
    /**
     * 学习目标：理解不同 HTTP 方法的注解
     * 
     * Spring MVC 提供的 HTTP 方法注解：
     * - @GetMapping：GET 请求（查询数据）
     * - @PostMapping：POST 请求（创建数据）
     * - @PutMapping：PUT 请求（更新数据，完整替换）
     * - @PatchMapping：PATCH 请求（更新数据，部分修改）
     * - @DeleteMapping：DELETE 请求（删除数据）
     * 
     * RESTful API 设计原则：
     * - GET：查询，不改变服务器状态
     * - POST：创建新资源
     * - PUT：完整更新资源
     * - PATCH：部分更新资源
     * - DELETE：删除资源
     * 
     * 本测试用例演示不同 HTTP 方法的请求
     */
    @Test
    public void test09_HTTP方法注解综合测试() throws Exception {
        log.info("========== 测试用例9：HTTP 方法注解综合测试 ==========");
        
        // TODO: 测试不同的 HTTP 方法
        // 提示：虽然当前 Controller 只实现了 GET 和 POST，
        // 但这里演示如何发送其他类型的请求
        
        // ========== 开始编写代码 ==========
        
        // GET 请求
        log.info("--- GET 请求 ---");
        mockMvc.perform(get("/api/chat/test"))
                .andExpect(status().isOk())
                .andDo(print());
        
        // POST 请求
        log.info("--- POST 请求 ---");
        mockMvc.perform(post("/api/chat/submit")
                        .param("userId", "user123")
                        .param("question", "测试"))
                .andExpect(status().isOk())
                .andDo(print());
        
        // PUT 请求（当前接口不支持，会返回 405 Method Not Allowed）
        log.info("--- PUT 请求（演示，当前接口不支持） ---");
        mockMvc.perform(put("/api/chat/test"))
                .andExpect(status().isMethodNotAllowed())  // 405 错误
                .andDo(print());
        
        // DELETE 请求（当前接口不支持，会返回 405 Method Not Allowed）
        log.info("--- DELETE 请求（演示，当前接口不支持） ---");
        mockMvc.perform(delete("/api/chat/test"))
                .andExpect(status().isMethodNotAllowed())  // 405 错误
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例9完成：理解了不同 HTTP 方法的注解");
    }

    // ============================================
    // 测试用例 10：综合应用测试
    // ============================================
    /**
     * 学习目标：综合运用所学知识
     * 
     * 测试场景：
     * 1. 提交聊天请求（POST + @RequestParam）
     * 2. 查询连接状态（GET + @PathVariable）
     * 3. 验证完整的请求-响应流程
     */
    @Test
    public void test10_综合应用测试() throws Exception {
        log.info("========== 测试用例10：综合应用测试 ==========");
        
        // TODO: 完成一个完整的业务流程测试
        // 步骤：
        // 1. 提交聊天请求，获取 requestId
        // 2. 使用 requestId 查询连接状态
        // 3. 验证整个流程
        
        // ========== 开始编写代码 ==========
        
        // 步骤1：提交聊天请求
        log.info("--- 步骤1：提交聊天请求 ---");
        MvcResult submitResult = mockMvc.perform(post("/api/chat/submit")
                        .param("userId", "user123")
                        .param("question", "综合测试问题")
                        .param("sessionId", "session-test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").exists())
                .andExpect(jsonPath("$.status").value("queued"))
                .andReturn();
        
        // 从响应中提取 requestId
        String responseContent = submitResult.getResponse().getContentAsString();
        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = objectMapper.readValue(responseContent, Map.class);
        String requestId = (String) responseMap.get("requestId");
        log.info("获取到的 requestId: {}", requestId);
        
        // 步骤2：使用 requestId 查询连接状态
        log.info("--- 步骤2：查询连接状态 ---");
        mockMvc.perform(get("/api/stream/status/{requestId}", requestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(requestId))
                .andExpect(jsonPath("$.connected").exists())
                .andDo(print());
        
        // ========== 代码编写结束 ==========
        
        log.info("✅ 测试用例10完成：综合应用测试通过");
    }

    // ============================================
    // 总结测试：验证所有知识点
    // ============================================
    /**
     * 总结测试：运行所有基础测试，验证学习成果
     */
    @Test
    public void test11_总结测试() throws Exception {
        log.info("============================================");
        log.info("开始总结测试：验证所有知识点");
        log.info("============================================");
        
        // 运行所有基础测试
        test01_GetMapping基础用法();
        test02_PostMapping基础用法();
        test03_RequestParam注解学习();
        test04_PathVariable注解学习();
        test05_RequestMapping类级别注解();
        test08_Produces属性学习();
        
        log.info("============================================");
        log.info("✅ 总结测试完成！");
        log.info("============================================");
        log.info("📚 你已经学习了以下注解：");
        log.info("   1. @RestController - 类级别，组合注解");
        log.info("   2. @RequestMapping - 类级别和方法级别路径映射");
        log.info("   3. @GetMapping - GET 请求映射");
        log.info("   4. @PostMapping - POST 请求映射");
        log.info("   5. @RequestParam - 获取请求参数");
        log.info("   6. @PathVariable - 获取路径变量");
        log.info("   7. produces - 指定响应内容类型");
        log.info("   8. @RequestBody - 获取请求体（扩展）");
        log.info("   9. @RequestHeader - 获取请求头（扩展）");
        log.info("============================================");
    }
}

