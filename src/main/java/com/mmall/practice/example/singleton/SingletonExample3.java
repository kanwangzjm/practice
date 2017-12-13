package com.mmall.practice.example.singleton;

import com.mmall.practice.annoations.NotRecommend;
import com.mmall.practice.annoations.ThreadSafe;

// 懒汉模式
@NotRecommend // 每次synchronized，性能不好
@ThreadSafe
public class SingletonExample3 {

    //私有构造函数
    private SingletonExample3() {
    }

    //单例对象
    private static SingletonExample3 instance = null;

    //静态工厂方法
    public static synchronized SingletonExample3 getInstance() {
        if (instance == null) { // 注意，只有第一次才彻底执行这里的代码
            instance = new SingletonExample3();//延迟实例化
        }
        return instance;
    }
}
