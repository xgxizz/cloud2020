package com.xgx.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 指定负载均衡规则
 * @author: xgx
 * @date: 2020/7/1 13:54
 */
@Configuration
public class CustomRule {

    @Bean
    public IRule myRule(){
        return new RandomRule();
    }
}
