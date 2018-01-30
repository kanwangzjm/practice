package com.mmall.practice.example.mq;

import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private Long id;

    private String msg;

    private Date sendTime;
}
