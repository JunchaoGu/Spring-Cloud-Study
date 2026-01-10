package com.xidian.nacosserviceb.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author: Student Gu
 * @create: 2026-01-10 00:06
 * @Description: TODO
 **/
@Component
public class XTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("token", String.valueOf(UUID.randomUUID()));

        System.out.println("=================已经添加UUID 作为Token===================");

    }
}



