package com.mmall.practice;

import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class Test {

    private static int thread_num = 5000;
    private static int client_num = 5000;

    private static AtomicInteger count = new AtomicInteger(0);

    private static int count2 = 0;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
//        final Semaphore semp = new Semaphore(thread_num);
        for (int index = 0; index < client_num; index++) {
            final int threadNum = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
//                        semp.acquire();
                        add();
                        log.info("Thread:{}, count2:{}", threadNum, count2);
//                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();
        log.info("count2:{}", count2);
    }

    private synchronized static void add() {
        count2++;
    }
}
