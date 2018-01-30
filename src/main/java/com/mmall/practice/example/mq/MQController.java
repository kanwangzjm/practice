package com.mmall.practice.example.mq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mq")
public class MQController {

    @Resource
    private RabbitMQClient rabbitMQClient;

    @RequestMapping("/send")
    @ResponseBody
    public String send(@RequestParam("message") String message) {
        rabbitMQClient.send(message);
        return "success";
    }
}
