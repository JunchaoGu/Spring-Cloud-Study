package com.xidian.nacosservicea;

import org.apache.catalina.filters.ExpiresFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * @author: Student Gu
 * @create: 2026-01-08 10:24
 * @Description: TODO
 **/
@SpringBootTest
public class NacosTest {


    @Autowired
    DiscoveryClient discoveryClient;

    @Test
    public void discoveryClientTest(){

        List<String> services = discoveryClient.getServices();

        for (String service : services) {
            System.out.println(service);

            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println(instance.getUri());
                System.out.println(instance.getHost());
                System.out.println(instance.getInstanceId());
                System.out.println(instance.getUri());
                System.out.println(instance.getPort());
            }

        }





    }
}



