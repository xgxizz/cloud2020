package com.xgx.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: <br/>
 * @author: xgx <br/>
 * date: 2020/9/9 10:47 <br/>
 */
@RestController
public class FlowLimitController {

        @GetMapping("/testA")
        public String testA() {
            return "------testA";
        }

        @GetMapping("/testB")
        public String testB() {
            return "------testB";
        }
}
