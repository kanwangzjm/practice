package com.mmall.practice.example.mq;

import com.mmall.practice.example.mq.kafka.KafkaSender;
import com.mmall.practice.example.mq.rabbitmq.RabbitMQClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mq")
public class MQController {

//    @Resource
    private RabbitMQClient rabbitMQClient;

    @Resource
    private KafkaSender kafkaSender;

    @RequestMapping("/send")
    @ResponseBody
    public String send(@RequestParam("message") String message) {
//        rabbitMQClient.send(message);
        kafkaSender.send(message);
        return "success";
    }
}
