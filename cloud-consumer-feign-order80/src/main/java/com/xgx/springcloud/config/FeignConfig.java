package com.xgx.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;

/**
 * Description: <br/>
 * 配置Feign的日志级别
 * @author: xgx <br/>
 * date: 2020/7/2 15:12 <br/>
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
