package com.mmall.practice.example.concurrent;

import com.mmall.practice.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * ConcurrentSkipListMap提供了一种线程安全的并发访问的排序映射表
 * 内部是SkipList（跳表）结构实现，在理论上能够在O(log(n))时间内完成查找、插入、删除操作
 * ConcurrentHashMap是HashMap的线程安全版本，ConcurrentSkipListMap是TreeMap的线程安全版本
 *
 * concurrentHashMap与ConcurrentSkipListMap性能测试
 * 在4线程1.6万数据的条件下，ConcurrentHashMap 存取速度是ConcurrentSkipListMap 的4倍左右
 *
 * 但ConcurrentSkipListMap有几个ConcurrentHashMap 不能比拟的优点：
 * 1、ConcurrentSkipListMap 的key是有序的。
 * 2、ConcurrentSkipListMap 支持更高的并发。ConcurrentSkipListMap 的存取时间是log（N），和线程数几乎无关。
 *      也就是说在数据量一定的情况下，并发的线程越多，ConcurrentSkipListMap越能体现出他的优势。
 *
 *  在非多线程的情况下，应当尽量使用TreeMap。
 *  此外对于并发性相对较低的并行程序可以使用Collections.synchronizedSortedMap将TreeMap进行包装，也可以提供较好的效率。
 *  对于高并发程序，应当使用ConcurrentSkipListMap，能够提供更高的并发度
 *  所以在多线程程序中，如果需要对Map的键值进行排序时，请尽量使用ConcurrentSkipListMap，可能得到更好的并发度
 */
@ThreadSafe
@Slf4j
public class ConcurrentExample2 {

    private static Map<Integer, Integer> map = new ConcurrentSkipListMap<>();

    private static int threadNum = 200;
    private static int clientNum = 5000;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(threadNum);
        for (int index = 0; index < clientNum; index++) {
            final int threadNum = index;
            exec.execute(() -> {
                try {
                    semp.acquire();
                    func(threadNum);
                    semp.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
        log.info("size:{}", map.size());
    }

    public static void func(int threadNum) {
        map.put(threadNum, threadNum);
    }
}
