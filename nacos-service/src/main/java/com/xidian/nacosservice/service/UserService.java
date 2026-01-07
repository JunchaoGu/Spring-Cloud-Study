package com.xidian.nacosservice.service;

import com.xidian.nacosservice.model.User;

import java.util.List;

/**
 * 用户服务接口
 * 用于演示Spring Cloud服务调用
 *
 * @author: Student Gu
 * @create: 2025-01-07
 */
public interface UserService {

    /**
     * 问候方法（与dubbo-service保持一致）
     *
     * @return 问候语
     */
    String sayHello();

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 创建用户
     *
     * @param user 用户对象
     * @return 创建后的用户对象（包含ID）
     */
    User createUser(User user);
}

