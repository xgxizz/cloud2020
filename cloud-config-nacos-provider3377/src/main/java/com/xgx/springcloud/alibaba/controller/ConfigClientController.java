package com.xgx.springcloud.alibaba.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope //使用spring cloud原生注解实现配置自动刷新
public class ConfigClientController
{
    // @Value("${config.info}")
    private String configInfo;
        

    // @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
 
 
