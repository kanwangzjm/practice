package com.mmall.practice.example.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、Java堆溢出
 */
public class HeapOOM {

    static class OOMObject {
    }

    public static void main(String[] args) throws Exception {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
