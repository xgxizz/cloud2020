package com.xgx.springcloud.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Description: <br/>
 * @author: xgx <br/>
 * date: 2020/8/24 15:44 <br/>
 */
public interface DataChannel {

    String TOPIC_INPUT = "input";//input对应application.yml中的spring.cloud.stream.bindings下的

    @Input(TOPIC_INPUT)
    SubscribableChannel dataInputChannel();
}
