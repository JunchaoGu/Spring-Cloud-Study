package com.xidian.nacosserviceb.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Student Gu
 * @create: 2026-01-09 17:11
 * @Description: TODO
 **/

@Component
@ConfigurationProperties(prefix = "order")
@Data
public class NacosBatchProperties {

    String timeOut;
    String autoConfirm;

}



