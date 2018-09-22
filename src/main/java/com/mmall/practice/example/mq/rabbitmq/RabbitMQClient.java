package com.mmall.practice.example.mq.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.COMMON_EXCHANGE;
import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.COMMON_ROUTING;
import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.DELAYED_EXCHANGE;
import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.DELAYED_ROUTING;

@Component
@Slf4j
public class RabbitMQClient {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(COMMON_EXCHANGE, COMMON_ROUTING, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);/**消息持久化*/
                log.info("common send {}", message);
                return message;
            }
        }, correlationId);
    }

    public void sendDelayed(String message) {
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE, DELAYED_ROUTING, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(3000);/**延时3秒发送*/
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);/**消息持久化*/
                log.info("delay send {}", message);
                return message;
            }
        }, correlationId);
    }
}
