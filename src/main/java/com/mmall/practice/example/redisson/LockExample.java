package com.mmall.practice.example.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 可重入锁
@Slf4j
public class LockExample {
    private static int clientTotal = 50;

    private static long count = 0;

    public static void main(String[] args) throws Exception {
        RedissonClient redisson = Redisson.create();

        RLock lock = redisson.getLock("lock");
//        RLock lock = redisson.getFairLock("lock");
        lock.lock(2, TimeUnit.SECONDS);

        final RCountDownLatch latch = redisson.getCountDownLatch("latch1");
        latch.trySetCount(clientTotal);

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int index = 0; index < clientTotal; index++) {
            final int threadNum = index;
            exec.execute(() -> {
                try {
                    RLock lock1 = redisson.getLock("lock");
                    lock1.lock();
                    try {
                        log.info("threadNum: {}", threadNum);
                        count++;
                    } finally {
                        lock1.unlock();
                    }
                    latch.countDown();
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        lock.unlock();
        latch.await();
        log.info("count:{}", count);
        exec.shutdown();
        redisson.shutdown();
    }
}
