package com.mmall.practice.example.mq.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.DELAYED_EXCHANGE;
import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.DELAYED_QUEUE;
import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.DELAYED_ROUTING;

@Configuration
public class RabbitDelayMQConfig {

    @Bean
    public DirectExchange defaultExchange() {
        DirectExchange directExchange = new DirectExchange(DELAYED_EXCHANGE, true, false);
        directExchange.setDelayed(true);
        return directExchange;
    }

    @Bean
    public Queue notifyQueue() {
        return new Queue(DELAYED_QUEUE, true);/**消息持久化*/
    }


    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(notifyQueue()).to(defaultExchange()).with(DELAYED_ROUTING);
    }
}
