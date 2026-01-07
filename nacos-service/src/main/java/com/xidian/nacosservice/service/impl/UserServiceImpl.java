package com.xidian.nacosservice.service.impl;

import com.xidian.nacosservice.model.User;
import com.xidian.nacosservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用户服务实现类
 *
 * Spring Cloud与Dubbo的区别：
 * - Dubbo使用 @DubboService 注解将服务发布到注册中心
 * - Spring Cloud使用 @Service 注解将服务注册到Spring容器，通过 @EnableDiscoveryClient 自动注册到Nacos
 * - Spring Cloud的服务调用通过OpenFeign或RestTemplate实现，而不是Dubbo的RPC协议
 *
 * @author: Student Gu
 * @create: 2025-01-07
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    /**
     * 模拟数据库存储
     */
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();

    /**
     * ID生成器
     */
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserServiceImpl() {
        // 初始化一些测试数据
        initTestData();
    }

    /**
     * 初始化测试数据
     */
    private void initTestData() {
        User user1 = new User(idGenerator.getAndIncrement(), "张三", "zhangsan@example.com", 25);
        User user2 = new User(idGenerator.getAndIncrement(), "李四", "lisi@example.com", 30);
        User user3 = new User(idGenerator.getAndIncrement(), "王五", "wangwu@example.com", 28);

        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
        userMap.put(user3.getId(), user3);

        log.info("初始化测试数据完成，共 {} 条用户记录", userMap.size());
    }

    @Override
    public String sayHello() {
        log.info("Spring Cloud服务调用触发...");
        log.info("这是通过OpenFeign或RestTemplate调用的RESTful接口，不是Dubbo RPC");
        return "Hello from Spring Cloud Nacos Service!";
    }

    @Override
    public User getUserById(Long id) {
        log.info("查询用户，ID: {}", id);
        User user = userMap.get(id);
        if (user == null) {
            log.warn("用户不存在，ID: {}", id);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("查询所有用户，共 {} 条记录", userMap.size());
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User createUser(User user) {
        log.info("创建用户: {}", user.getUsername());
        Long id = idGenerator.getAndIncrement();
        user.setId(id);
        userMap.put(id, user);
        log.info("用户创建成功，ID: {}", id);
        return user;
    }
}

