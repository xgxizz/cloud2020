package com.xgx.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Description: <br/>
 *
 * @author: xgx <br/>
 * date: 2020/7/27 13:14 <br/>
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulMain6588 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulMain6588.class, args);
    }
}
