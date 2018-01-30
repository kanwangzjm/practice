package com.mmall.practice.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample1 {

    // 修饰一个代码块，被修饰的代码块称为同步语句块，
    // 其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象
    public void test1() {

        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 - {}", i);
            }
        }
    }

    // 修饰一个方法，被修饰的方法称为同步方法，
    // 其作用的范围是整个方法，作用的对象是调用这个方法的对象； 
    public synchronized void test2() {

        for (int i = 0; i < 10; i++) {
            log.info("test2 - {}", i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample1 example1 = new SynchronizedExample1();
        SynchronizedExample1 example2 = new SynchronizedExample1();
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
