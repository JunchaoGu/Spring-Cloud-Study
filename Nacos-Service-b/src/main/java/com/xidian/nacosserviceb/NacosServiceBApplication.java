package com.xidian.nacosserviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * EnableDiscoveryClient
 * 自动注册：应用启动时，自动将服务实例信息（IP、端口、服务名）注册到Nacos
 *
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient //启动服务注册与发现功能
public class NacosServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosServiceBApplication.class, args);
    }

}
