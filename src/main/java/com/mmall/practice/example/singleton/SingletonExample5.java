package com.mmall.practice.example.singleton;

import com.mmall.practice.annoations.ThreadSafe;

// 懒汉模式: 双重同步锁单例模式
@ThreadSafe
public class SingletonExample5 {

    //私有构造函数
    private SingletonExample5() {
    }

    //单例对象  volatile -> 禁止指令重排
    private volatile static SingletonExample5 instance = null;

    //静态工厂方法
    public static SingletonExample5 getInstance() {
        if (instance == null) {      //双重检测机制
            synchronized (SingletonExample5.class){  //同步锁
                if (instance == null) {     //双重检测机制
                    instance = new SingletonExample5();
                }
            }
        }
        return instance;
    }
}
