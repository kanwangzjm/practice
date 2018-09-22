package com.mmall.practice.example.mq.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.COMMON_EXCHANGE;
import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.COMMON_QUEUE;
import static com.mmall.practice.example.mq.rabbitmq.QueueConstants.COMMON_ROUTING;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange(COMMON_EXCHANGE, true, false);
        return directExchange;
    }

    @Bean
    public Queue queue() {
        return new Queue(COMMON_QUEUE);
    }

    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(COMMON_ROUTING);
    }
}
