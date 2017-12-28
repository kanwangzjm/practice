package com.mmall.practice.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreExample1 {

    private static int threadNumTotal = 20;
    private static int clientNum = 5000;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(threadNumTotal);
        for (int index = 0; index < clientNum; index++) {
            final int threadNum = index;
            exec.execute(() -> {
                try {
                    semp.acquire();  // 获取一个许可
                    func(threadNum);
                    semp.release();  // 释放一个许可
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
    }

    public static void func(int threadNum) throws Exception {
        Thread.sleep(10000);
        log.info("Thread:{}", threadNum);
    }
}
