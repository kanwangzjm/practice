package com.mmall.practice.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// https://www.jianshu.com/p/cf12d4244171

/**
 *   Runnable的代码非常简单，它是一个接口且只有一个run()，创建一个类实现它，把一些费时操作写在其中，然后使用某个线程去执行该Runnable实现类即可实现多线程。
 *
 *   Callable的代码也非常简单，不同的是它是一个泛型接口，call()函数返回的类型就是创建Callable传进来的V类型。
 *   学习Callable对比着Runnable，这样就很快能理解它。
 *   Callable与Runnable的功能大致相似，Callable功能强大一些，就是被线程执行后，可以返回值，并且能抛出异常。
 *
 *   Future是一个接口，定义了Future对于具体的Runnable或者Callable任务的执行结果进行取消、查询任务是否被取消，查询是否完成、获取结果。
 *
 *   线程是属于异步计算模型，所以你不可能直接从别的线程中得到方法返回值。 这时候，Future就出场了。
 *   Future可以监视目标线程调用call的情况，当你调用Future的get()方法以获得结果时，当前线程就开始阻塞，直接call方法结束返回结果。
 *   Future引用对象指向的实际是FutureTask。
 *   也就是说，总结一句话，Future可以得到别的线程任务方法的返回值。
 */
@Slf4j
public class FutureTaskExample1 {

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("do something 1");
            Thread.sleep(5000);
            return "OK";
        }
    }

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        log.info("do something 2...");
        log.info("get async result：{}", future.get());
        log.info("Completed!");
    }

}
