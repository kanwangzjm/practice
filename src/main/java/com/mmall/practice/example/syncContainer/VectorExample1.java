package com.mmall.practice.example.syncContainer;

import lombok.extern.slf4j.Slf4j;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class VectorExample1 {

    private static Vector<Integer> list = new Vector<>();

    private static int threadNum = 200;
    private static int clientNum = 5000;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(threadNum);
        for (int index = 0; index < clientNum; index++) {
            final int threadNum = index;
            exec.execute(() -> {
                try {
                    semp.acquire();
                    func(threadNum);
                    semp.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
        log.info("size:{}", list.size());
    }

    public static void func(int threadNum) {
        list.add(threadNum);
    }
}
