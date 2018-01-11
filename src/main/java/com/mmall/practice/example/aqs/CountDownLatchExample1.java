package com.mmall.practice.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// http://blog.csdn.net/xianymo/article/details/46865469
@Slf4j
public class CountDownLatchExample1 {

    private static int threadNumTotal = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumTotal);
        for (int index = 0; index < threadNumTotal; index++) {
            Thread.sleep(1000);
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
        countDownLatch.await(); // 等待所有的进程执行结束
        log.info("finish");
        exec.shutdown();
    }

    public static void func(int threadNum, CountDownLatch countDownLatch) throws Exception {
        log.info("Thread:{}, count:{}", threadNum, countDownLatch.getCount());
        Thread.sleep(1000);
    }
}
