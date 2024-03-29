package com.example.designpattern.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;

/**
 * @author zhangzhongyuan@szanfu.cn
 * @description
 * @since 2023/3/14 9:41
 */
@SpringBootTest(classes = SingletonTest.class)

public class SingletonTest {

    @Test
    public void testLazy() {

        new Thread(() -> {
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println("1:" + instance);
        }).start();
        new Thread(() -> {
            LazySingleton instance = LazySingleton.getInstance();
            System.out.println("2:" + instance);
        }).start();
    }


    @Test
    public void testHungry() {


    }

    @Test
    public void testStaticInnerClass() {
        StaticInnerClassSingleton instance = StaticInnerClassSingleton.getInstance();
        StaticInnerClassSingleton instance2 = StaticInnerClassSingleton.getInstance();
        System.out.println(instance == instance2);
    }


    /**
     * 反射
     */
    @Test
    public void testReflect() throws Exception {
        //获取构造器
        Constructor<StaticInnerClassSingleton> declaredConstructor = StaticInnerClassSingleton.class.getDeclaredConstructor();
        //强制访问
        declaredConstructor.setAccessible(true);
        //反射创建对象
        StaticInnerClassSingleton instance = declaredConstructor.newInstance();

        //静态内部类创建对象
        StaticInnerClassSingleton instance2 = StaticInnerClassSingleton.getInstance();
        System.out.println(instance == instance2);
    }


    /**
     * 枚举
     */
    @Test
    public void testEnum() {
        EnumSingleton instance = EnumSingleton.INSTANCE;
        EnumSingleton instance2 = EnumSingleton.INSTANCE;
        System.out.println(instance == instance2);
    }


}

/**
 * 单例类 懒加载
 */
class LazySingleton {
    //1.内部静态变量 volatile 防止指令重排
    private volatile static LazySingleton instance;

    //2.私有构造器
    private LazySingleton() {
    }

    //3.静态共有方法 加入同步处理的代码，解决线程安全问题
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                    //1.分配内存空间
                    //2.执行构造方法，初始化对象
                    //3.把这个对象指向这个空间

                }
            }
        }

        return instance;
    }
}

/**
 * 单例类 饿汉式
 */
class HungrySingleTon {
    //1.内部静态变量
    private static HungrySingleTon instance = new HungrySingleTon();

    //2.私有构造器
    private HungrySingleTon() {
    }

    //3.静态共有方法
    public static HungrySingleTon getInstance() {
        return instance;
    }

}


/**
 * 单例类 静态内部类
 */
class StaticInnerClassSingleton {
    //1.私有构造器
    private StaticInnerClassSingleton() {
        //防止反射破坏单例
        if (StaticInnerClass.INSTANCE != null) {
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    //2.静态内部类
    private static class StaticInnerClass {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    //3.静态共有方法
    public static StaticInnerClassSingleton getInstance() {
        return StaticInnerClass.INSTANCE;
    }
}


/**
 * 单例类 枚举
 */
enum EnumSingleton {
    INSTANCE;

    public void sayOk() {
        System.out.println("ok");
    }
}



