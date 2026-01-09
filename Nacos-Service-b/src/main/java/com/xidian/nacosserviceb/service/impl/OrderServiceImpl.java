package com.xidian.nacosserviceb.service.impl;

import com.xidian.entity.Order;
import com.xidian.nacosserviceb.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author: Student Gu
 * @create: 2026-01-09 10:20
 * @Description: TODO
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(Long productId, Long userId) {

        Order order = new Order();
        order.setId(01L);
        order.setAddress("xidian");
        order.setNickName("hzangsan");
        order.setTotalAmount(BigDecimal.valueOf(1213));
        order.setUserId(userId);

        return order;
    }
}



