package com.xgx.springcloud.controller;

import com.xgx.springcloud.sink.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class SendMessageController {
    @Resource
    private IMessageProvider messageProvider;

    @GetMapping(value = "/sendMessage")
    public String sendMessage()
    {
        messageProvider.send();
        return "消息发送成功";
    }

}
 
 
