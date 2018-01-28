package com.mmall.practice.example.sync;

public class SynchronizedExample1 {

    public void test1() {

        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                // ...
            }
        }

    }

    public synchronized void test2() {

        for (int i = 0; i < 10; i++) {
            // ...
        }
    }
}
