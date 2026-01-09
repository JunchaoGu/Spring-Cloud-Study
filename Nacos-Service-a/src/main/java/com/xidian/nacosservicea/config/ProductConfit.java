package com.xidian.nacosservicea.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Student Gu
 * @create: 2026-01-09 14:00
 * @Description: TODO
 *
 * 全局配置类，Spring发送请求有 RestTemplate，而他是全局安全的因此不用每次都new，全局共享一份即可，因此配置到配置类中最合适；
 **/


@Configuration
public class ProductConfit {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}



