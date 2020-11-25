package com.example.learn2020;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 胡乱起的名字
 * 对应享元模式。
 */
public class ShareEntity {


}

class TreeFactory {
    private static Map<String,Tree> trees = new HashMap<>();
    public static Tree getTree(String name,String data) {
        if (trees.containsKey(name)) {
            return trees.get(name);
        }
        Tree tree = new Tree(name,data);
        trees.put(name,tree);
        return tree;
    }
}

class Tree {
    private final String name;
    private final String data;

    public Tree(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }


    public String getData() {
        return data;
    }
}
