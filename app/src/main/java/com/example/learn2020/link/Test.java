package com.example.learn2020.link;

import java.util.Stack;

public class Test {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(3);
        ListNode node2 = new ListNode(2);
        head.next = node1;
        node1.next = node2;


        reversePrint(head);
    }

    public static int[] reversePrint(ListNode head) {
        Stack<ListNode> reversed = new Stack<>();
        ListNode mHead = head;
        while (mHead != null) {
            reversed.push(mHead);
            mHead = mHead.next;
        }
        int[] arr = new int[reversed.size()];
        int cur = 0;
        while (!reversed.isEmpty()) {
            arr[cur ++] = reversed.pop().val;
        }
        return arr;
    }


}
