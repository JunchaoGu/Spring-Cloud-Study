package com.xidian.nacosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Nacos服务提供者启动类
 *
 * @EnableDiscoveryClient: 启用服务发现功能，自动注册到Nacos
 *
 * @author: Student Gu
 * @create: 2025-01-07
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("Nacos服务提供者启动成功！");
        System.out.println("服务已注册到Nacos注册中心");
        System.out.println("========================================");
    }
}

