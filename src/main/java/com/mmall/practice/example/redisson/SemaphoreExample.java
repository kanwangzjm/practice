package com.mmall.practice.example.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SemaphoreExample {

    private static int threadNumTotal = 7;
    private static int clientNum = 50;

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        // 分布式信号量
        RSemaphore s = redisson.getSemaphore("test");
        s.trySetPermits(threadNumTotal);
        s.release(threadNumTotal);

        // 分布式闭锁
        final RCountDownLatch latch = redisson.getCountDownLatch("latch1");
        latch.trySetCount(clientNum);

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int index = 0; index < clientNum; index++) {
            final int threadNum = index;
            exec.execute(() -> {
                try {
                    RSemaphore s1 = redisson.getSemaphore("test");
                    s1.acquire();  // 获取一个许可
                    log.info("thread {} finish", threadNum);
                    s1.release();  // 释放一个许可
                    latch.countDown();
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        latch.await();
        log.info("all thread finish");
        exec.shutdown();
        redisson.shutdown();
    }

}
