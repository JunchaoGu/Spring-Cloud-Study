package com.xidian.nacosserviceb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xidian.entity.Order;
import com.xidian.nacosserviceb.properties.NacosBatchProperties;
import com.xidian.nacosserviceb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: Student Gu
 * @create: 2026-01-09 10:18
 * @Description: TODO
 **/

@RefreshScope // TODO 自动刷新注解
@RestController
public class OrderController {


    @Autowired
    OrderService orderService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/create/{userId}/{productId}")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId
    ) {

        Order order = orderService.createOrder(userId, productId);

        return order;
    }

    @GetMapping("/catList")
    public String catListOrder() {

//        1.获取所有订单消息
        List<String> services = discoveryClient.getServices();

        List<String> list =new ArrayList<>();

        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                list.add("http://" + instance.getHost() + ":" +instance.getPort());
            }
        }

        return list+"";

    }

    @GetMapping("/random/discovery1")
    public String randomDiscovery(){
        List<ServiceInstance> instances = discoveryClient.getInstances("Nacos-Service-a");

        ServiceInstance serviceInstance = instances.get(new Random().nextInt(instances.size()));

        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String url = "http://"+host+":"+port+"/product/155";
        String forObject = restTemplate.getForObject(url, String.class);
        return forObject;
    }








    @GetMapping("/choose/balancer")
    public String testBalancer(){

        ServiceInstance choose = loadBalancerClient.choose("Nacos-Service-a");
        System.out.println(choose.getPort());
        ServiceInstance choose1 = loadBalancerClient.choose("Nacos-Service-a");
        System.out.println(choose1.getPort());

        ServiceInstance choose2= loadBalancerClient.choose("Nacos-Service-a");
        System.out.println(choose2.getPort());

        ServiceInstance choose3 = loadBalancerClient.choose("Nacos-Service-a");
        System.out.println(choose3.getPort());

        ServiceInstance choose4 = loadBalancerClient.choose("Nacos-Service-b");
        System.out.println(choose4.getPort());

        return null;
    }

    @GetMapping("/random/discovery")
    public String randomDiscovery1() {
        return restTemplate.getForObject(
                "http://Nacos-Service-a/product/155",
                String.class
        );
    }

    @Value("${order.timeout}")
    String timeout;

    @Value("${order.auto-confirm}")
    String autoConfirm;

    @GetMapping("/getConfig")
    public String getConfig() {
        return timeout + " " + autoConfirm;
    }

    @Autowired
    NacosBatchProperties nacosBatchProperties;

    @GetMapping("/getConfig")
    public String getBatchConfig() {
        return nacosBatchProperties.getTimeOut() + "    " + nacosBatchProperties.getAutoConfirm();
    }





}



