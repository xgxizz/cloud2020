package com.xgx.springcloud.sink;

import com.alibaba.fastjson.JSONObject;
import com.xgx.springcloud.producer.DataProducer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Description: <br/>
 *
 * @author: xgx <br/>
 * date: 2020/8/11 17:49 <br/>
 */
@EnableBinding(Source.class)
public class MessageProviderImpl implements IMessageProvider {
    @Resource
    private DataProducer dataProducer;//消息发送通道
    @Override
    public void send() {

        String serial = UUID.randomUUID().toString();
        JSONObject msg = new JSONObject();
        msg.put("uuid", UUID.randomUUID().toString());
        msg.put("msg", "hello.");
        dataProducer.sendPositionMessage(msg.toJSONString());
        System.out.println("------------->>serial:" + serial);
    }

}
