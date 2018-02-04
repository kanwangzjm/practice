package com.mmall.practice.example.singleton;

import com.mmall.practice.annoations.ThreadSafe;

/*
 * 饿汉模式
 * 单例的实例在类装载时进行创建。由于是在类装时候创建， 所以能够保证线程安全。如果单例类的构造方法中没有包含过多的操作处理
 * 饿汉式其实是可以接受的
 *
 * 不足：
 * 如果构造方法中存在过多的处理，会导致加载这个类时比较慢，可能引起性能问题。
 * 如果使用饿汉式的话，只进行了类的装载，并没有实质的调用，会造成资源的浪费。
 */
@ThreadSafe
public class SingletonExample2 {

    //私有构造函数
    private SingletonExample2() {
    }

    //单例对象
    private static SingletonExample2 instance = null;

    static {
        instance = new SingletonExample2();
    }

    //静态工厂方法
    public static SingletonExample2 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }
}
