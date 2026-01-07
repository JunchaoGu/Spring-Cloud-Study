package com.xidian.kafkaconsumer.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 性能对比实验
 * 目标：直观感受不同并发度对处理速度的影响
 */
@Component
public class PerformanceComparisonListener {

    // === 组1：单线程 ===
    private final AtomicInteger count1 = new AtomicInteger(0);
    private final AtomicLong startTime1 = new AtomicLong(0);

    @KafkaListener(
        topics = "test-topic",
        groupId = "perf-concurrency-1",
        concurrency = "1"
    )
    public void concurrency1(ConsumerRecord<String, String> record) {
        processMessage("并发度=1", count1, startTime1, record);
    }

    // === 组2：三线程 ===
    private final AtomicInteger count3 = new AtomicInteger(0);
    private final AtomicLong startTime3 = new AtomicLong(0);

    @KafkaListener(
        topics = "test-topic",
        groupId = "perf-concurrency-3",
        concurrency = "3"
    )
    public void concurrency3(ConsumerRecord<String, String> record) {
        processMessage("并发度=3", count3, startTime3, record);
    }

    // === 组3：六线程 ===
    private final AtomicInteger count6 = new AtomicInteger(0);
    private final AtomicLong startTime6 = new AtomicLong(0);

    @KafkaListener(
        topics = "test-topic",
        groupId = "perf-concurrency-6",
        concurrency = "6"
    )
    public void concurrency6(ConsumerRecord<String, String> record) {
        processMessage("并发度=6", count6, startTime6, record);
    }

    /**
     * 统一的消息处理逻辑
     */
    private void processMessage(String groupName, AtomicInteger counter, 
                                AtomicLong startTime, ConsumerRecord<String, String> record) {
        // 记录开始时间
        if (counter.get() == 0) {
            startTime.set(System.currentTimeMillis());
        }

        // 模拟业务处理（50ms）
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int currentCount = counter.incrementAndGet();

        // 每处理10条消息输出一次统计
        if (currentCount % 10 == 0) {
            long elapsed = System.currentTimeMillis() - startTime.get();
            double throughput = (currentCount * 1000.0) / elapsed;

            System.out.printf("📊 [%s] 已处理: %3d 条 | 耗时: %5d ms | 吞吐量: %.2f msg/s | 线程: %s%n",
                groupName, 
                currentCount, 
                elapsed, 
                throughput,
                Thread.currentThread().getName()
            );
        }
    }
}