package com.xgx.springcloud.alibaba.service;

// @Component
public class ServiceFeignFallback implements ServiceFeignClient {
    @Override
    public String echo(String str) {
        return "error";
    }

    @Override
    public String timeout() {
        return "";
    }
}
