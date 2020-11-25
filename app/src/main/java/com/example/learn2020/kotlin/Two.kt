package com.example.learn2020.kotlin

import java.util.*


fun main() {

    val node0 = ListNode(1)
    val node1 = ListNode(2)
    val node2 = ListNode(3)
    val node3 = ListNode(3)
    val node4 = ListNode(2)
    val node5 = ListNode(1)
    node0.next = node1
    node1.next = node2
    node2.next = node3
    node3.next = node4
    node4.next = node5
 var head =  removeDuplicateNodes(node0)
    while (head != null) {
        println(head.`val`)
        head = head.next
    }
}

fun removeDuplicateNodes(head: ListNode?): ListNode? {
    var headNode = head
    while (headNode != null) {
        var pre = headNode
        var next:ListNode? = headNode.next
        while (next != null) {
            if (headNode.`val` == next.`val`) {
                pre?.next = pre?.next?.next
            } else {
                pre = pre?.next
            }
            next = next.next
        }
        headNode = headNode.next
    }
    return head
}

class ListNode(var `val`: Int) {
        var next: ListNode? = null
}

fun kidsWithCandies(candies: IntArray, extraCandies: Int): BooleanArray{
    candies.forEach {  }
    val max = candies.max() ?: 0
    return candies.map { it + extraCandies >= max }.toBooleanArray()
}
