package com.xidian.nacosserviceb.service;

import com.xidian.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * @author: Student Gu
 * @create: 2026-01-09 10:19
 * @Description: TODO
 **/

@Repository
public interface OrderService {

    Order createOrder(Long productId, Long userId);

}



