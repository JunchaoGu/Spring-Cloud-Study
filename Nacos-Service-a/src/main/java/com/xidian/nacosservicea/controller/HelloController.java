package com.xidian.nacosservicea.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Service A 测试控制器
 *
 * 编写目的：
 * 1. 验证服务是否正常启动
 * 2. 提供HTTP接口供外部调用测试
 * 3. 展示服务基本信息（服务名、端口等）
 * 4. 为后续服务调用（OpenFeign/RestTemplate）提供测试端点
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    // 从配置文件注入服务名称
    @Value("${spring.application.name}")
    private String serviceName;

    // 从配置文件注入端口号
    @Value("${server.port}")
    private String port;

    /**
     * 基础Hello接口
     * 访问地址：http://localhost:8081/api/hello
     */
    @GetMapping("/hello")
    public String hello() {
        return "👋 Hello from " + serviceName + " (Port: " + port + ")";
    }

    /**
     * 详细信息接口
     * 访问地址：http://localhost:8081/api/info
     * 返回JSON格式的服务信息
     */
    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> result = new HashMap<>();
        result.put("serviceName", serviceName);
        result.put("port", port);
        result.put("timestamp", LocalDateTime.now());
        result.put("message", "Service A is running successfully!");
        result.put("version", "1.0.0");
        return result;
    }

    /**
     * 服务健康检查接口（自定义）
     * 访问地址：http://localhost:8081/api/health
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", serviceName);
        return result;
    }
}