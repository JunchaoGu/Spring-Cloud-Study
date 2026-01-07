package com.xidian.dubboweb.controller;

import com.xidian.dubboservice.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Student Gu
 * @create: 2025-12-10 10:09
 * @Description: TODO
 **/

@RestController
@RequestMapping("/user")
public class UserController {
    // 使用正确的接口类型，从dubbo-service模块导入
    @DubboReference
    private UserService userService;


    @RequestMapping("/sayHello")
    public String sayHello(){
        return userService.sayHello();
    }

}