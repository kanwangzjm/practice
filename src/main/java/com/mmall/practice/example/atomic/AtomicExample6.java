package com.mmall.practice.example.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 更新AtomicExample6类里面的 testInt 字段（必须是volatile且不是static对象）
 * update.compareAndSet()方法使用cas机制，每次提交的时候都比较下 test.testInt 是不是100，如果是，则更新
 */
@Slf4j
public class AtomicExample6 {

    private static AtomicIntegerFieldUpdater<AtomicExample6> update = AtomicIntegerFieldUpdater.newUpdater(AtomicExample6.class, "testInt");
    private static AtomicExample6 test = new AtomicExample6();
    public volatile int testInt = 100;

    public static void main(String[] args) {
        if (update.compareAndSet(test, 100, 120)) {
            log.info("已修改,{}", test.getTestInt());
        }
    }

    private int getTestInt() {
        return testInt;
    }
}
