package com.xidian.dubboservice.service.impl;

import com.xidian.dubboservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author: Student Gu
 * @create: 2025-12-10 09:30
 * @Description: TODO
 **/

@Slf4j
//@Service  //传统的方式，将Service注入Spring的IoC容器进行管理；Bean的声明；
@DubboService()     //构建DubboService，将服务发布；       将访问地址放到注册中心；
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello() {

        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");
        log.info("远程调用触发。。。");

        return "hello";
    }
}



