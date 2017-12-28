package com.mmall.practice.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchExample2 {

    private static int threadNumTotal = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumTotal);
        for (int index = 0; index < threadNumTotal; index++) {
            final int threadNum = index;
            exec.execute(() -> {
                try {
                    func(threadNum, countDownLatch);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(10, TimeUnit.MICROSECONDS); // 只等待指定的时间，可以提前结束
        log.info("finish");
        exec.shutdown();
    }

    public static void func(int threadNum, CountDownLatch countDownLatch) throws Exception {
        Thread.sleep(100);
        log.info("Thread:{}, count:{}", threadNum, countDownLatch.getCount());
    }
}
