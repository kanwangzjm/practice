package com.mmall.practice.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask的父类是RunnableFuture，而RunnableFuture继承了Runnbale和Future这两个接口
 * 1、FutureTask最终都是执行Callable类型的任务。
 * 2、如果构造函数参数是Runnable，会被Executors.callable方法转换为Callable类型。
 *
 * FutureTask实现了两个接口，Runnable和Future，所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值
 * 那么这个组合的使用有什么好处呢？
 * 假设有一个很费时逻辑需要计算并且返回这个值，同时这个值不是马上需要，那么就可以使用这个组合，用另一个线程去计算返回值，
 * 而当前线程在使用这个返回值之前可以做其它的操作，等到需要这个返回值时，再通过Future得到
 *
 * 通过Executor执行线程任务都是以Callable形式，如果传入Runnable都会转化为Callable。
 * 通过new Thread(runnable)，只能是Runnable子类形式
 */
@Slf4j
public class FutureTaskExample2 {

    public static void main(String[] args) {

        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
                log.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });

        new Thread(future).start();
        log.info("do something in main");
        try {
            Thread.sleep(3000);// do something
            log.info("result: {}",future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
