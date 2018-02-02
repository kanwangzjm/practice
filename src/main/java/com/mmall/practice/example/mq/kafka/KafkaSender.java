package com.mmall.practice.example.mq.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mmall.practice.example.mq.Message;
import com.mmall.practice.example.mq.QueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class KafkaSender {

//    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    public void send(String msg) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(msg);
        message.setSendTime(new Date());
        log.info("send message:{}", message);
        kafkaTemplate.send(QueueConstants.TEST, gson.toJson(message));
    }
}
