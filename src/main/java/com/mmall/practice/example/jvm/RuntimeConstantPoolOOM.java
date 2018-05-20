package com.mmall.practice.example.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 4、方法和运行时常量池溢出
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}

