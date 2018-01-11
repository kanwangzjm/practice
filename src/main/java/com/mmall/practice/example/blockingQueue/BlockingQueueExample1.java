package com.mmall.practice.example.blockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class BlockingQueueExample1 {

    public static void main(String[] args) {

        final BlockingQueue queue = new ArrayBlockingQueue(3);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        log.info(Thread.currentThread().getName() + "准备放数据!");
                        queue.put(1);
                        log.info(Thread.currentThread().getName() + "已经放了数据，队列目前有" + queue.size() + "个数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    //将此处的睡眠时间分别改为100和1000，观察运行结果
                    Thread.sleep(1000);
                    log.info(Thread.currentThread().getName() + "准备取数据!");
                    queue.take();
                    log.info(Thread.currentThread().getName() + "已经取走数据，队列目前有" + queue.size() + "个数据");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
