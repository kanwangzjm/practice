package com.mmall.practice.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample2 {

    // 修饰一个静态的方法
    // 其作用的范围是整个静态方法，作用的对象是这个类的所有对象
    public static synchronized void test1() {

        for (int i = 0; i < 10; i++) {
            log.info("test1 - {}", i);
        }
    }

    // 修饰一个类，
    // 其作用的范围是synchronized后面括号括起来的部分，作用主的对象是这个类的所有对象。
    public static void test2() {

        synchronized (SynchronizedExample2.class) {

            for (int i = 0; i < 10; i++) {
                log.info("test2 - {}", i);
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedExample2 example1 = new SynchronizedExample2();
        SynchronizedExample2 example2 = new SynchronizedExample2();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(() -> {
            try {
                example1.test1();
            } catch (Exception e) {
                log.error("exception1", e);
            }
        });
        exec.execute(() -> {
            try {
                example2.test2();
            } catch (Exception e) {
                log.error("exception2", e);
            }
        });

        exec.shutdown();
    }
}
