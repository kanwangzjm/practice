package com.mmall.practice.example;

import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@ThreadSafe
public class CountExample6 {

    private static int threadTotal = 20;
    private static int clientTotal = 50000;

    private static LongAdder countLongAddr = new LongAdder();

    private static AtomicLong countAtomicLong = new AtomicLong(0);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            testAtomicLong();
            testLongAddr();
            System.out.println();
        }
    }

    private static void testAtomicLong() {
        ExecutorService exec = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();

        for (int index = 0; index < clientTotal; index++) {
            exec.execute(() -> {
                try {
                    countAtomicLong.incrementAndGet();
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
        log.info("AtomicLong cost:{}, count:{}", (System.currentTimeMillis() - start), countAtomicLong.get());
    }

    private static void testLongAddr() {
        ExecutorService exec = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        for (int index = 0; index < clientTotal; index++) {
            exec.execute(() -> {
                try {
                    countLongAddr.increment();
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
        log.info("LongAdder  cost:{}, count:{}", (System.currentTimeMillis() - start), countLongAddr);
    }
}
