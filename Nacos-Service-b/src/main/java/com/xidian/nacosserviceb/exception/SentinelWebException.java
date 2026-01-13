package com.xidian.nacosserviceb.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xidian.nacosserviceb.common.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * @author: Student Gu
 * @create: 2026-01-12 10:32
 * @Description: TODO
 **/

@Component
public class SentinelWebException implements BlockExceptionHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       BlockException e) throws Exception {

        R<Object> error = R.error(R.ERROR_CODE, "Sentinel发生Web级别报错，报错信息是：" + e.getMessage());

        String json = objectMapper.writeValueAsString(error);

        String contentType = "utf-8";

        httpServletResponse.setContentType(contentType);

        PrintWriter printWriter = httpServletResponse.getWriter();

        printWriter.write(json);

        printWriter.flush();
        printWriter.close();
    }
}



