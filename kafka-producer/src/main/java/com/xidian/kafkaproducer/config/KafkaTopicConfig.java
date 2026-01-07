package com.xidian.kafkaproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka Topic 配置类
 * 作用：应用启动时自动创建Topic
 */
@Configuration  // ✅ 告诉Spring：这是一个配置类
public class KafkaTopicConfig {

    /**
     * ✅ 方法1：使用TopicBuilder（推荐，可读性强）
     * // 等价于：
     * // NewTopic topic = new NewTopic(...);
     * // Spring容器.register(topic);
     *
     */
    @Bean  // ✅ 这个注解让Spring管理这个对象
    public NewTopic testTopic() {
        return TopicBuilder.name("test-topic")         // Topic名称
                .partitions(6)                          // 6个分区
                .replicas(2)                            // 2个副本
                .config("retention.ms", "86400000")     // 消息保留1天（毫秒）
                .config("segment.bytes", "1073741824")  // 日志段大小1GB
                .build();
    }

    /**
     * ✅ 方法2：直接new NewTopic（简洁）
     */
    @Bean
    public NewTopic orderTopic() {
        // 参数：Topic名称, 分区数, 副本数
        return new NewTopic("order-topic", 3, (short) 2);
    }

    /**
     * ✅ 方法3：从配置文件读取（灵活）
     */
    @Bean
    public NewTopic logTopic(
            @Value("${kafka.topics.log-topic.partitions:6}") int partitions,
            @Value("${kafka.topics.log-topic.replicas:2}") short replicas
    ) {
        return TopicBuilder.name("log-topic")
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }
}