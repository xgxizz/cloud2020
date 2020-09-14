package com.xgx.springcloud.sink;

import com.xgx.springcloud.channel.DataChannel;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Description: <br/>
 *
 * @author: xgx <br/>
 * date: 2020/8/26 17:56 <br/>
 */
@EnableBinding(DataChannel.class)
public class MessageSinker {

    @Value("${server.port}")
    private String serverPort;

    private static AtomicInteger num = new AtomicInteger(0);

    @StreamListener(DataChannel.TOPIC_INPUT)
    public void saveToEs(Message<String> message,
                         @Header(KafkaHeaders.CONSUMER) Consumer<?, ?> consumer,
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {

        //初始化 ack
        Acknowledgment ack = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        num.incrementAndGet();
        String payload = message.getPayload();
        System.out.println(serverPort + "订阅到的msg:" + payload);
        ack.acknowledge();
    }
}
