package com.xidian.nacosservicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //启动服务注册与发现功能
public class NacosServiceAApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosServiceAApplication.class, args);
    }

}
