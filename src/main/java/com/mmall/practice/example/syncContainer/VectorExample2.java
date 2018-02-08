package com.mmall.practice.example.syncContainer;

import java.util.Vector;

public class VectorExample2 {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) throws InterruptedException {

        while (true) {

            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread thread1 = new Thread() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            };
            Thread thread2 = new Thread() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.add(i);
                    }
                }
            };

            thread1.start();
            thread2.start();
        }
    }
}
