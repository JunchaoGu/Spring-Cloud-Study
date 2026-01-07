package com.xidian.chatservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author: Student Gu
 * @create: 2025-12-22 10:48
 * @Description: TODO
 **/
@SpringBootTest
@RestController
public class TestSseEmitterManager {

    @Test
    public void basicSend(){
        SseEmitter sseEmitter = new SseEmitter(60*1000L);//60秒超时




    }



}



