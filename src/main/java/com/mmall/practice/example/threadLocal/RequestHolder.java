package com.mmall.practice.example.threadLocal;

public class RequestHolder {

    private static final ThreadLocal<Long> requestHolder = new ThreadLocal<Long>();

    public static void add(Long id) {
        requestHolder.set(id);
    }

    public static Long getId() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }
}
