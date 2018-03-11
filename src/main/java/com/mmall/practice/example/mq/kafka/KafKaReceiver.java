package com.mmall.practice.example.mq.kafka;

import com.mmall.practice.example.mq.QueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class KafKaReceiver {

    @KafkaListener(topics = {QueueConstants.TEST})
    public void receive(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record);
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();

            log.info("record:{}", record);
            log.info("message:{}", message);
        }
    }
}
