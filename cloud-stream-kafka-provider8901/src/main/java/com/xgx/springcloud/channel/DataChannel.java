package com.xgx.springcloud.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Description: <br/>
 * @author: xgx <br/>
 * date: 2020/8/24 15:44 <br/>
 */
public interface DataChannel {

    String TOPIC_OUTPUT = "input";//output对应application.yml中的spring.cloud.stream.bindings下的

    @Output(TOPIC_OUTPUT)
    MessageChannel dataOutputChannel();
}
