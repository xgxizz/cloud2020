package com.xgx.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description: <br/>
 * @author: xgx <br/>
 * date: 2020/8/26 17:45 <br/>
 */
@SpringBootApplication
@EnableEurekaClient
public class StreamKafkaConsumerMain8902 {
    public static void main(String[] args) {
        SpringApplication.run(StreamKafkaConsumerMain8902.class, args);
    }
}
