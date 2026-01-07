//package com.xidian.kafkaconsumer.listener;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author: Student Gu
// * @create: 2025-12-12 12:06
// * @Description: TODO
// **/
//
////@Component         //暂时注释，避免干扰；
//public class KafkaMessageListener {
//
//    @KafkaListener(topics = "test-topic")
//    public void listen(String message, ConsumerRecord<String,String> record){
//        System.out.println("=======================================");
//        System.out.println("收到消息:" + message);
//        System.out.println("=======================================");
//
//        System.out.println("======================================");
//        System.out.println("Topic: " + record.topic());
//        System.out.println("Partition: " + record.partition());
//        System.out.println("Offset: " + record.offset());
//        System.out.println("Key: " + record.key());
//        System.out.println("Value: " + record.value());
//        System.out.println("Timestamp: " + record.timestamp());
//        System.out.println("======================================");
//
//    }
//
//}
//
//
//
