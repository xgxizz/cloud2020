package com.xgx.springcloud.producer;

import com.xgx.springcloud.channel.DataChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * Description: <br/>
 * @author: xgx <br/>
 * date: 2020/8/26 17:02 <br/>
 */
@EnableBinding(DataChannel.class)
public class DataProducer {
    @Resource
    private DataChannel dataChannel;

    public void sendPositionMessage(String message) {

        dataChannel.dataOutputChannel().send(MessageBuilder.withPayload(message).build());
    }
}
