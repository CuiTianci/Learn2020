package com.example.learn2020;

import com.example.learn2020.stackqueue.MinStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;

public class Study {

//    ["MinStack","push","push","push","min","pop","min"]
//            [[],[-2],[0],[-3],[],[],[],[]]
    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        System.out.println(stack.min());
        stack.pop();
        System.out.println(stack.min());
    }
}
