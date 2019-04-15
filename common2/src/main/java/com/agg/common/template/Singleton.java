package com.agg.common.template;

public class Singleton {
    // 懒汉式双重检查锁方式:
    private static volatile Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    // 静态内部类方式:
//    private Singleton() {
//    }
//
//    static class SingletonHolder{
//        static Singleton singleton = new Singleton();
//    }
//
//    public static Singleton getInsatnce(){
//        return SingletonHolder.singleton;
//    }
}
