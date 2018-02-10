package com.mmall.practice.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SemaphoreExample3 {

    private static int threadNumTotal = 20;
    private static int clientNum = 5000;
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(threadNumTotal);
        for (int index = 0; index < clientNum; index++) {
            final int threadNum = index;
            exec.execute(() -> {
                try {
                    if (semp.tryAcquire(3)) {  // 尝试获取许可
                        log.info("acquire:{}", count.addAndGet(1));
                        func(threadNum);
                        semp.release();  // 释放许可
                    }
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

/**
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
 *  at java.lang.Thread.start0(Native Method)
 *  at java.lang.Thread.start(Thread.java:714)
 *  at java.util.concurrent.ThreadPoolExecutor.addWorker(ThreadPoolExecutor.java:950)
 *  at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1368)
 *  at com.mmall.practice.example.aqs.SemaphoreExample2.main(SemaphoreExample2.java:20)
 *
 *   这个异常问题本质原因是我们创建了太多的线程，而能创建的线程数是有限制的，导致了异常的发生。能创建的线程数的具体计算公式如下：
 *  (MaxProcessMemory - JVMMemory - ReservedOsMemory) / (ThreadStackSize) = Number of threads
 *
 *  MaxProcessMemory   指的是一个进程的最大内存
 *  JVMMemory          JVM内存
 *  ReservedOsMemory   保留的操作系统内存
 *  ThreadStackSize    线程栈的大小
 *
 *   http://sesame.iteye.com/blog/622670
 *
 * */
