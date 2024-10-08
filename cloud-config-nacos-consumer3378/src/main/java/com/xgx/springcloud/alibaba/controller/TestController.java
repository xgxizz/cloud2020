package com.xgx.springcloud.alibaba.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xgx.springcloud.alibaba.service.ServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ServiceFeignClient feignClient;

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-service-provider/echo/" + str, String.class);
    }

    @GetMapping(value = "/echo2/{str}")
    public String echo2(@PathVariable("str") String str) {
        return feignClient.echo(str);
    }

    // 超时降级演示
    @HystrixCommand(fallbackMethod = "timeoutHandler", commandProperties = {
            // 超过1.5秒就降级自己
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    @GetMapping("/time")
    public String time() {
        return feignClient.timeout();
    }

    // 降级方法
    public String timeoutHandler() {
        return "远程调用服务超时，调用失败";
    }


    @HystrixCommand(
            fallbackMethod = "defaultFallBack",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 熔断时间窗口
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50") // 失败率阈值
            }
    )
    @GetMapping("break")
    public String testBreak() {
        return feignClient.echo("123");
    }

    public String defaultFallBack() {
        return "远程调用服务出错";
    }
}