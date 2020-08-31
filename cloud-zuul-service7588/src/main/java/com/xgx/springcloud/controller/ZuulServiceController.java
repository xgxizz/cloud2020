package com.xgx.springcloud.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: <br/>
 *
 * @author: xgx <br/>
 * date: 2020/7/27 15:28 <br/>
 */
@RestController
public class ZuulServiceController {

    @RequestMapping("/zuul/sayHello")
    public String sayHello(String name){
        return "hello ! " + name;
    }

}
