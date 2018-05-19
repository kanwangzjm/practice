package com.mmall.practice.example.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CountDownLatchExample {

    private static int threadNumTotal = 20;

    public static void main(String[] args) throws Exception {

        // connects to 127.0.0.1:6379 by default
        log.info("init redisson client instance");
        RedissonClient redisson = Redisson.create();
        log.info("redisson client instance finish");

        ExecutorService exec = Executors.newFixedThreadPool(6);

        // 分布式闭锁
        final RCountDownLatch latch = redisson.getCountDownLatch("latch1");
        latch.trySetCount(threadNumTotal);


        for (int i = 0; i < threadNumTotal; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    Thread.sleep(1000);
                    log.info("thread {} finish", threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
                latch.countDown();
            });
        }
        latch.await();
        log.info("all threads finish");
        exec.shutdown();
        redisson.shutdown();
//        exec.awaitTermination(10, TimeUnit.SECONDS);
    }

}
