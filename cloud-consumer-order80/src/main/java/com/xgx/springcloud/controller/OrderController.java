package com.xgx.springcloud.controller;

import com.xgx.springcloud.entities.CommonResult;
import com.xgx.springcloud.entities.Payment;
//import com.xgx.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
//import java.net.URI;
//import java.util.List;

@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

//    @Resource
//    private LoadBalancer loadBalancer;

//    @Resource
//    private DiscoveryClient discoveryClient;


    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);  //写操作
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    //返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else {
            return new CommonResult<>(444, "操作失败");
        }
    }

    /**
     * @description: 自定义负载均衡器，不用@LoadBalanced注解，需要测试的时候将文件夹lb改为包，取消下方注释，并import相关的包
     * @author: xgx
     * @date: 2020/7/1 15:33
     * @return:
     */
//    @GetMapping(value = "/consumer/payment/lb/")
//    public String getPaymentLB(){
//        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
//        if (instances == null || instances.size() <= 0){
//            return null;
//        }
//        ServiceInstance serviceInstance = loadBalancer.instances(instances);
//        URI uri = serviceInstance.getUri();
//        return restTemplate.getForObject(uri+"/payment/lb",String.class);
//    }

}
 
 
