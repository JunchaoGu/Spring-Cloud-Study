package com.xidian.chatservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
public class TestObjectMapper {

    public static class User {
        private Long id;
        private String name;
        private OffsetDateTime createdAt;

        public User() {}

        public User(Long id, String name, OffsetDateTime createdAt) {
            this.id = id;
            this.name = name;
            this.createdAt = createdAt;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public OffsetDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testObjectMapperBasic() throws Exception {
        /**
         * ObjectMapper 是 Jackson 库的核心类，其核心能力是实现
         * Java 对象与 JSON 之间的相互转换，也就是序列化（Serialization）
         * 和反序列化（Deserialization）
         */
        // 确保 DateTime 模块已注册
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        User u = new User(10L, "Eve", OffsetDateTime.of(2025, 12, 22, 12, 0, 0, 0, ZoneOffset.UTC));

//        序列化方式，objectMapper.writeValueAsString()
        String json = objectMapper.writeValueAsString(u);
        log.info(json);

        log.info("****************************************");
        log.info("****************************************");
        log.info("****************************************");

        log.info(u.getCreatedAt().toString());
//        反序列化方式readValue
        User fromJson = objectMapper.readValue(json, User.class);

        log.info(fromJson.toString());
        assertEquals(u.getId(), fromJson.getId());
        assertEquals(u.getName(), fromJson.getName());
        assertEquals(u.getCreatedAt(), fromJson.getCreatedAt());
    }

    // 你也可以追加其他测试，与方案 A 的测试内容保持一致
}