package com.xgx.springcloud.alibaba.service;

import com.xgx.springcloud.alibaba.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "nacos-service-provider", fallback = ServiceFeignFallback.class)
@FeignClient(name = "nacos-service-provider", configuration = FeignConfig.class)
public interface ServiceFeignClient {
    @GetMapping(path = "echo2/{str}")
    String echo(@PathVariable("str") String str);

    @GetMapping("timeout")
    String timeout();
}
