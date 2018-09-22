package com.mmall.practice.example.mq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQServer {

    @RabbitListener(queues = QueueConstants.COMMON_QUEUE)
    public void receive(String message) {
        log.info("common queue receive msg: {}", message);
    }

    @RabbitListener(queues = QueueConstants.DELAYED_QUEUE)
    public void receiveDelayed(String message) {
        log.info("delayed queue receive msg: {}", message);
    }
}
