package com.xidian.nacosservice.controller;

import com.xidian.nacosservice.model.User;
import com.xidian.nacosservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务Controller
 * 暴露RESTful接口供其他服务调用
 *
 * Spring Cloud与Dubbo的区别：
 * - Dubbo: 服务接口通过RPC协议调用，不需要Controller
 * - Spring Cloud: 服务通过HTTP RESTful接口调用，需要Controller暴露接口
 *
 * @author: Student Gu
 * @create: 2025-01-07
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 问候接口（与dubbo-service保持一致）
     * GET /user/sayHello
     */
    @GetMapping("/sayHello")
    public String sayHello() {
        log.info("收到sayHello请求，当前服务端口: {}", serverPort);
        return userService.sayHello();
    }

    /**
     * 根据ID获取用户
     * GET /user/{id}
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("收到获取用户请求，ID: {}", id);
        return userService.getUserById(id);
    }

    /**
     * 获取所有用户
     * GET /user/list
     */
    @GetMapping("/list")
    public List<User> getAllUsers() {
        log.info("收到获取所有用户请求");
        return userService.getAllUsers();
    }

    /**
     * 创建用户
     * POST /user
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("收到创建用户请求，用户名: {}", user.getUsername());
        return userService.createUser(user);
    }

    /**
     * 获取服务实例信息（用于调试服务发现）
     * GET /user/instance
     */
    @GetMapping("/instance")
    public Map<String, Object> getInstanceInfo() {
        log.info("收到获取服务实例信息请求");

        Map<String, Object> info = new HashMap<>();
        info.put("serviceName", "nacos-service");
        info.put("port", serverPort);

        // 获取当前服务实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-service");
        info.put("instanceCount", instances.size());

        List<Map<String, String>> instanceDetails = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            Map<String, String> detail = new HashMap<>();
            detail.put("host", instance.getHost());
            detail.put("port", String.valueOf(instance.getPort()));
            detail.put("uri", instance.getUri().toString());
            instanceDetails.add(detail);
        }
        info.put("instances", instanceDetails);

        return info;
    }

    /**
     * 健康检查接口
     * GET /user/health
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "nacos-service");
        health.put("port", serverPort);
        health.put("timestamp", System.currentTimeMillis());
        return health;
    }
}

