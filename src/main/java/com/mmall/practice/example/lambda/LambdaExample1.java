package com.mmall.practice.example.lambda;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
public class LambdaExample1 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int min = IntStream.of(nums).min().getAsInt();
        log.info("{}", min);

        new Thread(() -> log.info("thread running")).start();

        Runnable runnable1 = () -> log.info("runnable running");
        new Thread(runnable1).start();

        InterfaceExample interface1 = (i -> i * 2);
        InterfaceExample interface2 = (i) -> i * 2;
        InterfaceExample interface3 = (int i) -> i * 2;
        InterfaceExample interface4 = (int i) -> {
            log.info("{}", i);
            return  i * 2;
        };
        log.info("interface1:{}", interface1.doubleNum(1));
        log.info("interface2:{}", interface2.doubleNum(2));
        log.info("interface3:{}", interface3.doubleNum(3));
        log.info("interface4:{}", interface4.doubleNum(4));
        log.info("interface:{}", interface1.addNum(4, 5));

    }
}
