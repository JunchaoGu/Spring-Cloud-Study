package com.xidian.nacosserviceb.config;

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

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){

        return  new RestTemplate();
    }
}



