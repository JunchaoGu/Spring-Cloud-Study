//package com.xidian.kafkaconsumer.listener;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * 实验目标：清晰看到线程与分区的映射关系
// */
//@Component
//public class PartitionMappingListener {
//
//    // 使用线程安全的集合记录每个线程处理过的分区
//    // Key: 线程名称, Value: 该线程处理过的分区集合        服务不会停，因此堆内存中的消息不会清空；
//    private final Map<String, Set<Integer>> threadPartitionMap = new ConcurrentHashMap<>();
//
//    @KafkaListener(
//            topics = "test-topic",
//            groupId = "partition-mapping-group",
//            concurrency = "3"  // 3个线程
//    )
//    public void listen(ConsumerRecord<String, String> record) {
//        String threadName = Thread.currentThread().getName();
//
//        int partition = record.partition();
//
//        // 记录这【当前线程】处理了哪个分区
//        threadPartitionMap
//                .computeIfAbsent(threadName, k -> new CopyOnWriteArraySet<>())
//                .add(partition);
//
//        // 打印当前消息信息
//        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//        System.out.println("┃ 消息: " + record.value());
//        System.out.println("┃ 线程: " + threadName);
//        System.out.println("┃ 分区: " + partition);
//        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
//        System.out.println("┃ 当前线程-分区映射关系：");
//
//        // 打印完整的映射关系
//        threadPartitionMap.forEach((thread, partitions) -> {
//            System.out.println("┃   " + thread);
//            System.out.println("┃   └─> 负责分区: " + partitions);
//        });
//
//        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");
//
//        // 模拟处理
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}