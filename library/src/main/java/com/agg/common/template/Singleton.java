package com.agg.common.template;

/**
 * 为什么使用单例？
 * 一个类专门提供一些公共功能供别人调用，而本身并不会处理业务逻辑。那么创建多个实例，会消耗内存，造成不必要的开销。此时需要单例。
 * 单例是什么？
 * 让整个生命周期内只有一个实例。
 * <p>
 * 一般单例模式有如下五种写法：懒汉式，双重校验锁，静态内部类，饿汉式和枚举。
 * 懒汉式：非线程安全
 * 双重校验锁：线程安全
 * 静态内部类：线程安全
 * 饿汉式：线程安全
 * 枚举：线程安全
 */

///**
// * 懒汉式1：原始版，不建议使用，会有多线程并发问题。
// * 由于instance是静态的，因此能够保证程序运行过程中只存在一个实例。
// * 但是针对多线程情况，就可能有问题。当有多个线程同时执行到line行时，会各自new一个对象出来。存在多个实例，违背了单例模式的概念。
// */
//public class Singleton{
//    private static Singleton instance;
//    private Singleton() {}
//    public static Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton(); // line
//        }
//        return instance;
//    }
//}

///**
// * 懒汉式2：改进懒汉式1，不建议使用，多线程高并发时效率低。
// */
//public class Singleton{
//    private static Singleton instance;
//    private Singleton() {}
//
//    // 对方法加锁
//    public static synchronized Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }
//
////    // 对变量加锁
////    public static Singleton getInstance() {
////        synchronized (Singleton.class) {
////            if (instance == null) {
////                instance = new Singleton();
////            }
////            return instance;
////        }
////    }
//}

/**
 * 懒汉式3：改进懒汉式2，double-checked lock(DCL)双重校验锁方式。
 * 并不是每次进入getInstance方法都要获得锁。对instance访问分为两类：读和写。仅当instance为null的时候才会写，此时获得锁；其他情况就只是读操作，此时不需要获得锁了。
 *
 * DCL的好处：第一次创建实例的时候会同步所有线程，以后有线程再想获取Singleton的实例就不需要进行同步，直接返回实例即可。
 * 小插曲之JDK1.5之前DCL的小缺点：JDK1.5之前的Java中无法安全使用DCL来实现单例模式，尽管得到了Singleton的正确引用，
 * 但是却有可能访问到其成员变量的不正确值，因为volatile屏蔽指令重排序的语义在JDK1.5中才被完全修复。
 * 可参见java的“happen-before”八大原则规则的第三条，volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作。
 */
public class Singleton{
    private volatile static Singleton instance;
    private Singleton() {}
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

///**
// * 静态内部类单例模式
// * 可以同时保证延迟加载和线程安全。这种方式同样利用了类加载机制来保证只创建一个instance实例。它与饿汉式一样，也是利用了类加载机制，因此不存在多线程并发的问题。
// */
//public class Singleton {
//    private static class SingletonHolder {
//        private static final Singleton instance = new Singleton();
//    }
//    private Singleton() {}
//    public static Singleton getInstance() {
//        return SingletonHolder.instance;
//    }
//}

///**
// * 饿汉式
// */
//public class Singleton {
//    private static Singleton instance = new Singleton();
//    private Singleton() {}
//    public static Singleton getInstance() {
//        return instance;
//    }
//}

///**
// * 枚举
// */
//public enum Singleton {
//    INSTANCE;
//}




