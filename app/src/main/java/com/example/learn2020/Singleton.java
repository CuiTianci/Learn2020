package com.example.learn2020;

public class Singleton {

    public static void main(String[] args) {
        Product productA = new AFactory().create();
        productA.show();
        Product productB = new BFactory().create();
        productB.show();
    }
}

class LazySingleton {
    private volatile static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (null == instance) {
            synchronized (LazySingleton.class) {
                if (null == instance)
                    instance = new LazySingleton();
            }
        }
        return instance;
    }
}

class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();
    private HungrySingleton(){}

    public static HungrySingleton getInstance() {
        return instance;
    }

}

abstract class Product {
    public abstract void show();
}

class ProductA extends  Product {
    @Override
    public void show() {
        System.out.println("aaaaa");
    }
}

class ProductB extends Product {
    @Override
    public void show() {
        System.out.println("bbb");
    }
}

abstract class Factory {
    public abstract Product create();
}

class AFactory extends Factory {
    @Override
    public Product create() {
        return new ProductA();
    }
}

class BFactory extends  Factory {
    @Override
    public Product create() {
        return new ProductB();
    }
}
