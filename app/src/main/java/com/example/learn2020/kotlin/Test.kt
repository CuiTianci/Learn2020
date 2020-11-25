package com.example.learn2020.kotlin

import java.util.*
import kotlin.collections.HashSet
import kotlin.math.max


fun main() {
//    println(longestConsecutive(intArrayOf(100,4,200,1,3,2)))
//    print(maxScoreSightseeingPair(intArrayOf(3, 7, 2, 3)))
//    print(isPalindrome("A man, a plan, a canal: Panama"))
//    val utilsClass = Class.forName("com.example.learn2020.Utils")
//    val utilsConstructor = utilsClass.constructors[0]
//    utilsConstructor.isAccessible = true
//    val utils = utilsConstructor.newInstance()
//    val shoutMethod = utilsClass.getDeclaredMethod("shout")
//    shoutMethod.isAccessible = true
//    shoutMethod.invoke(utils)
}

fun longestConsecutive(nums: IntArray): Int {
    var max = 0
    val numSet: HashSet<Int> = HashSet()
    nums.forEach {
        numSet.add(it)
    }
    nums.forEach {
        if (!numSet.contains(it - 1)) {
            var curNum = it
            var length = 1
            while (numSet.contains(curNum + 1)) {
                curNum++
                length++
            }
            max = Math.max(max, length)
        }
    }
    return max
}

fun isPalindrome(x: Int): Boolean {
    val str = x.toString()
    var i = 0
    var j = str.length - 1
    while (i < j) {
        if (str[i] != str[j])
            return false
        i++
        j--
    }
    return true
}

fun maxScoreSightseeingPair(A: IntArray): Int {
    var i = 0
    var j = A.size - 1
    var ans = 0
    while (i < j) {
        val res = A[i] + A[j] + i - j
        ans = ans.coerceAtLeast(res)
        if (A[i] > A[j]) {
            j--
        } else {
            i++
        }
    }
    return ans
}

fun isPalindrome(s: String): Boolean {
    val str = s.toUpperCase(Locale.ROOT)
    var i = 0
    var j = str.length - 1
    while (i < j) {
        if (!str[i].isLetterOrDigit()) {
            i++
            continue
        }
        if (!str[j].isLetterOrDigit()) {
            j--
            continue
        }
        if (str[i] != str[j]) return false
        i++
        j--
    }
    return true
}
