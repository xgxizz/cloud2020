package com.xgx.springcloud.sink;

import com.xgx.springcloud.channel.DataChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;


/**
 * Description: <br/>
 *
 * @author: xgx <br/>
 * date: 2020/8/26 17:56 <br/>
 */
@EnableBinding(DataChannel.class)
public class MessageSinker {

    @StreamListener(DataChannel.TOPIC_INPUT)
    public void saveToEs(Message<String> message) {
        String payload = message.getPayload();
        System.out.println("----接收到的msg:" + payload);
    }
}
