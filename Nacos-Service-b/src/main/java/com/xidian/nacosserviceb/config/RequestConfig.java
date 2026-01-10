package com.xidian.nacosserviceb.config;

import feign.Logger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Student Gu
 * @create: 2026-01-09 14:41
 * @Description: TODO
 **/

@Configuration
public class RequestConfig {

    /**
     * Feign客户端日志记录
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){

        return  new RestTemplate();
    }
}



