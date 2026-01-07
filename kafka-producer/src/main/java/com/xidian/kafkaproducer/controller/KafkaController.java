package com.xidian.kafkaproducer.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Student Gu
 * @create: 2025-12-12 11:37
 * @Description: TODO
 **/

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaTemplate<String,String> kafkaTemplate;


    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/send")                                                //HTTP请求参数在传输时永远是字符串，没有数字、布尔等类型概念 因此Controller默认值是String类型
    public String send(@RequestParam String msg,@RequestParam(defaultValue = "10") int count){

        String topic = "test-topic";


        for (int i = 0; i < count; i++) {

            kafkaTemplate.send(topic,"key" + i,msg);
        }
        return "消息已经发送到 Kafka-topic" + topic + "msg = " + msg;

    }










}



