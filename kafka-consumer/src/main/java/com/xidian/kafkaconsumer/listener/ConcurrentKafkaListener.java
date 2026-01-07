//package com.xidian.kafkaconsumer.listener;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ConcurrentKafkaListener {
//
//    // 基础版本：concurrency = 1（单线程）
//    @KafkaListener(
//            topics = "test-topic",
//            groupId = "single-thread-group",
//            concurrency = "1"
//    )
//    public void singleThreadConsumer(ConsumerRecord<String, String> record) {
//        processMessage("单线程消费", record);
//    }
//
//    // 并发版本：concurrency = 3（3个线程）
//    @KafkaListener(
//            topics = "test-topic",
//            groupId = "multi-thread-group",
//            concurrency = "3"
//    )
//    public void multiThreadConsumer(ConsumerRecord<String, String> record) {
//        processMessage("3线程并发消费", record);
//    }
//
//    // 高并发版本：concurrency = 6（6个线程，匹配分区数）
//    @KafkaListener(
//            topics = "test-topic",
//            groupId = "high-concurrency-group",
//            concurrency = "6"
//    )
//    public void highConcurrencyConsumer(ConsumerRecord<String, String> record) {
//        processMessage("6线程高并发消费", record);
//    }
//
//    private void processMessage(String consumerType, ConsumerRecord<String, String> record) {
//        System.out.println("┌─────────────────────────────────────────────────────");
//        System.out.println("│ 【消费者类型】: " + consumerType);
//        System.out.println("│ 【线程名称】: " + Thread.currentThread().getName());
//        System.out.println("│ 【消息内容】: " + record.value());
//        System.out.println("│ 【分区】: " + record.partition());
//        System.out.println("│ 【偏移量】: " + record.offset());
//        System.out.println("└─────────────────────────────────────────────────────\n");
//
//        // 模拟处理时间
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}