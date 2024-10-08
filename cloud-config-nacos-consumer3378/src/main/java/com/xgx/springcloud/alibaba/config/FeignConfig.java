package com.xgx.springcloud.alibaba.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;  // 禁用重试
    }
}
