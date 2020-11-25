package com.example.learn2020.stackqueue;

import java.util.Stack;


public class MinStack {

    Stack<Integer> data;
    Stack<Integer> assist;
    /** initialize your data structure here. */
    public MinStack() {
        data = new Stack<>();
        assist = new Stack<>();
    }

    public void push(int x) {
        data.push(x);
        if (assist.empty() || assist.peek() > x) {
            assist.push(x);
        }
    }

    public void pop() {
        if (data.empty()) return;
        int x =  data.pop();
        if (!assist.empty() && x == assist.peek()) {
            assist.pop();
        }
    }

    public int top() {
        return data.empty() ? -1 : data.peek();
    }

    public int min() {
        return assist.empty() ? -1 : assist.peek();
    }
}