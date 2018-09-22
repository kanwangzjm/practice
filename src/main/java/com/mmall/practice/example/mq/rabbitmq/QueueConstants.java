package com.mmall.practice.example.mq.rabbitmq;

public interface QueueConstants {

    /** 消息交换机的名字*/
    String COMMON_EXCHANGE = "common_exchange";
    /** 队列key*/
    String COMMON_ROUTING = "common_routing";
    /** 队列名*/
    String COMMON_QUEUE = "common_queue";

    /** 延迟消息交换机的名字*/
    String DELAYED_EXCHANGE = "delayed_exchange";
    /** 延迟队列key*/
    String DELAYED_ROUTING = "delayed_routing";
    /** 延迟队列名*/
    String DELAYED_QUEUE = "delayed_queue";

}
