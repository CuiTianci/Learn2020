package com.example.learn2020;

import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

class LRUCache {


    public static int lastRemaining(int n) {
        if (n <= 1) return n;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1;i <= n;i ++) {
            list.add(i);
        }
        int c = -1;
        int direction;
        while (list.size() > 1) {
            if (c < 0) {
                c = 0;
                direction = 1;
            } else {
                c = list.size() - 1;
                direction = -1;
            }
            while (c >= 0 && c <= list.size() - 1) {
                list.remove(c);
                c += 2 *  direction;
                if (direction > 0) {
                    c -= 1;
                }
            }
        }
        return list.get(0);
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2 /* 缓存容量 */);

        TimeCalculator.getInstance().record();
System.out.println(lastRemaining(1000000));
TimeCalculator.getInstance().showTime();


//        cache.put(1, 1);
//        cache.put(2, 2);
//        print(cache.get(1));      // 返回  1
//        cache.put(3, 3);    // 该操作会使得密钥 2 作废
//        print(cache.get(2));       // 返回 -1 (未找到)
//        cache.put(4, 4);    // 该操作会使得密钥 1 作废
//        print(cache.get(1));       // 返回 -1 (未找到)
//        print(cache.get(3));       // 返回  3
//        print(cache.get(4));       // 返回  4
//        print(cache.get(1));
////        print(cache.put(2,1));
//        cache.put(2,1);
//        cache.put(3,2);
//        print(cache.get(2));
//        print(cache.get(3));
//        cache.put(2, 1);
//        print(cache.get(2));
//        cache.put(2, 1);
//        cache.put(2, 2);
//        print(cache.get(2));
//        cache.put(4, 1);
//        print(cache.get(2));
    }


    private static void print(Object o) {
        System.out.println(o.toString());
    }


    private int count;
    private int capacity;
    private ListNode dummy;
    private ListNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        count = 0;
        dummy = new ListNode(null, null, -1, -1);//哑节点。
    }

    public int get(int key) {
        ListNode existNode = containsKey(key);
        if (null != existNode) {
            if (tail != existNode) {
                existNode.prev.next = existNode.next;
                existNode.next.prev = existNode.prev;
                existNode.prev = tail;
                tail.next = existNode;
                tail = tail.next;
                tail.next = null;
            }
            return existNode.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (capacity == 1) {
            tail = new ListNode(dummy,null,key,value);
            tail.prev = dummy;
            dummy.next = tail;
            return;
        }
        ListNode existNode = containsKey(key);
        if (null == existNode) {
            if (count < capacity) {//未满
                ListNode node = new ListNode(null, null, key, value);
                addNode(node);
            } else {
                ListNode head = dummy.next;
                head.key = key;
                head.val = value;
                dummy.next = dummy.next.next;
                dummy.next.prev = dummy;
                head.prev = tail;
                tail.next = head;
                tail = tail.next;
                tail.next = null;
            }
        } else {//已经有对应key。
            existNode.val = value;
            if (tail != existNode) {
                existNode.prev.next = existNode.next;
                existNode.next.prev = existNode.prev;
                tail.next = existNode;
                existNode.prev = tail;
                tail = tail.next;
                tail.next = null;
            }
        }
    }

    private void addNode(ListNode newNode) {
        if (dummy.next == null) {
            tail = newNode;
            tail.prev = dummy;
            dummy.next = tail;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            tail.next = null;
        }
        count++;
    }

    //判断是否已经有对应key
    private ListNode containsKey(int key) {
        ListNode head = dummy.next;
        while (head != null) {
            if (head.key == key) {
                break;
            }
            head = head.next;
        }
        return head;
    }

    //双向链表节点。
    class ListNode {
        ListNode prev;
        ListNode next;
        int key;
        int val;

        public ListNode(ListNode prev, ListNode next, int key, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
            this.key = key;
        }
    }
}