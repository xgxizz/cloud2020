package com.xgx.springcloud.service;

import com.xgx.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Description: <br/>
 *
 * @author: xgx <br/>
 * date: 2020/7/2 14:29 <br/>
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    @GetMapping(value = "/payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id") Long id);//调用该接口，就可以调用注册中心的服务

    @GetMapping(value = "/payment/feign/timeout")
    String paymentFeignTimeout();//测试feign超时

}
