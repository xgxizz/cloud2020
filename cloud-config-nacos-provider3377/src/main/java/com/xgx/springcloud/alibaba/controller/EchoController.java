package com.xgx.springcloud.alibaba.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class EchoController {
    private static int temp = 0;

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }

    @RequestMapping(value = "/echo2/{string}", method = RequestMethod.GET)
    public String echo2(@PathVariable String string) {
        // 使用temp模拟服务端发生异常
        temp++;
        System.out.println("当前temp=" + temp);
        if (temp % 2 == 0) {
            return "Hello Nacos Discovery " + string;
        } else {
            throw new RuntimeException("系统异常");
        }
    }


    /**
     * 设置自身降级
     * 5秒钟以内就是正常的业务逻辑
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    @GetMapping("/timeout")
    public String timeout() {
        try {
            // 睡眠3秒
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("接收到客户端请求，准备返回");
        return "hello";
    }

    // 降级后方法,上面方法出问题,TimeoutHandler来处理，返回一个出错信息
    public String TimeoutHandler() {
        return "访问请求失败了，服务不可用";
    }
}
