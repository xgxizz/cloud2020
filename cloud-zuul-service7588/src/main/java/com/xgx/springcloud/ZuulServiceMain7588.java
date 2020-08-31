package com.xgx.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description: <br/>
 *
 * @author: xgx <br/>
 * date: 2020/7/27 15:23 <br/>
 */
@SpringBootApplication
@EnableEurekaClient
public class ZuulServiceMain7588 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServiceMain7588.class, args);
    }
}
