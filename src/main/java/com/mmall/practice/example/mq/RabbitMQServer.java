package com.mmall.practice.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQServer {

    @RabbitListener(queues = RabbitQueueConstants.TEST)
    private void receive(String message) {
        log.info("{}", message);
    }
}
