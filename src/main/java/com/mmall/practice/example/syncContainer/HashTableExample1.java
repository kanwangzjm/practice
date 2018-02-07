package com.mmall.practice.example.syncContainer;

import com.google.common.collect.Maps;
import com.mmall.practice.annoations.NotThreadSafe;
import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@ThreadSafe
@Slf4j
public class HashTableExample1 {

    private static Map<Integer, Integer> map = new Hashtable<>();

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
        log.info("size:{}", map.size());
    }

    public static void func(int threadNum) {
        map.put(threadNum, threadNum);
    }
}
