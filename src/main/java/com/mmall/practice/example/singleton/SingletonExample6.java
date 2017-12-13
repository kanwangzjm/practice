package com.mmall.practice.example.singleton;

import com.mmall.practice.annoations.Recommend;
import com.mmall.practice.annoations.ThreadSafe;

/*
 * 枚举模式: 最安全
 */
@ThreadSafe
@Recommend
public class SingletonExample6 {

    private SingletonExample6() {
    }

    public static SingletonExample6 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {

        INSTANCE;

        private SingletonExample6 singleton;

        //JVM会保证此方法绝对只调用一次
        Singleton() {
            singleton = new SingletonExample6();
        }

        public SingletonExample6 getInstance() {
            return singleton;
        }
    }
}
