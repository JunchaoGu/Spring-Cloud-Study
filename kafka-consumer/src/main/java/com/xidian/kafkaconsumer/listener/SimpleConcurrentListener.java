//package com.xidian.kafkaconsumer.listener;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author: Student Gu
// * @create: 2025-12-13 10:31
// * @Description: TODO
// **/
//
//@Component
//public class SimpleConcurrentListener {
//
//    /**
//     * 单线程消费测试
//     */
//
//    @KafkaListener(
//            topics = "test-topic",
//            groupId = "single-thread-group",
////            concurrency = "1"           //并发单个线程；
//            concurrency = "3"           //并发3个线程；
//    )
//    public void singleThreadConsumer(ConsumerRecord<String,String> record){
//
//        String threadName = Thread.currentThread().getName();
//
////        模拟消息处理耗时
//
//        // 模拟消息处理耗时（100毫秒）
//        long startTime = System.currentTimeMillis();
//        try {
//            Thread.sleep(100); // 模拟业务处理
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        long endTime = System.currentTimeMillis();
//
//        // 打印详细信息
////        http://localhost:7001/kafka/send?msg=message-1
//        System.out.println("╔════════════════════════════════════════╗");
//        System.out.println("║ 【三线程消费者】                        ║");
//        System.out.println("╠════════════════════════════════════════╣");
//        System.out.println("║ 线程名称: " + threadName);
//        System.out.println("║ 消息内容: " + record.value());
//        System.out.println("║ 来自分区: " + record.partition());
//        System.out.println("║ 偏移量:   " + record.offset());
//        System.out.println("║ 处理耗时: " + (endTime - startTime) + " ms");
//        System.out.println("╚════════════════════════════════════════╝\n");
//
//
//
//    }
//
//
//
//
//}
//
//
//
