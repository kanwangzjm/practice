package com.mmall.practice.example.sync;

public class SynchronizedExample2 {

    public static synchronized void test1() {

        for (int i = 0; i < 10; i++) {
            // ...
        }
    }

    public static void test2() {

        synchronized (SynchronizedExample2.class) {

            for (int i = 0; i < 10; i++) {
                // ...
            }
        }
    }
}
