package com.mmall.practice.example.lambda;

@FunctionalInterface
public interface InterfaceExample {

    int doubleNum(int i);

    default int addNum(int i, int j) {
        return i + j;
    }
}
