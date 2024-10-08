package com.xgx.springcloud.controller;

import com.xgx.springcloud.entities.CommonResult;
import com.xgx.springcloud.entities.Payment;
import com.xgx.springcloud.sink.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);
        if (result>0){  //成功
            return new CommonResult(200,"插入数据库成功,serverPort:" + serverPort,result);
        }else {
            return new CommonResult(444,"插入数据库失败,serverPort:" + serverPort,null);
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果："+payment);
        if (payment!=null){  //说明有数据，能查询成功
            return new CommonResult(200,"查询成功,serverPort:" + serverPort,payment);
        }else {
            return new CommonResult(444,"没有对应记录，查询ID："+id + " serverPort:" + serverPort,null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){//OpenFeign默认等待1s,超过后报错。
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @RequestMapping("/zuul")
    public String testZuul(){
        return "Hello Zuul !!!" + serverPort;
    }
}
 
 
