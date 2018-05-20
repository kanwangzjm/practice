package com.mmall.practice.example.jvm;

/**
 * 3、创建线程导致内存溢出
 * 这里,在Windows平台的虚拟机中，Java的线程是映射到操作系统的内核线程上，这里可能导致机器假死。
 */
public class JavaVMStackOOM {

    private void doStop() {
        while (true) {
        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(() -> {
                doStop();
            });
            thread.start();
        }
    }

    public static void main(String[] args) throws Exception {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}

