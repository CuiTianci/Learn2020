package com.example.learn2020.suanfa;

import com.example.learn2020.TimeCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class StringDecode {

    public static void main(String[] args) {
        TimeCalculator.getInstance().record();
        System.out.println(decodeString("2[abc]xyc3[z]"));
        TimeCalculator.getInstance().showTime();
    }

    public static String decodeString(String s) {
        int leftMCount = 0;
        int pre = -1;
        for (int i = 0;i < s.length();i ++) {
            if (pre == -1 && Character.isDigit(s.charAt(i))) {
                pre = i;
            }
            if ('[' == s.charAt(i)) {
                leftMCount++;
            }
            if (']' == s.charAt(i)) {
                leftMCount --;
            }
            if (leftMCount >= 2) {
                break;
            }
        }
        if (pre == -1) return s;
        if (leftMCount == 0) {//不再存在嵌套关系。
            List<Integer> leftM = new ArrayList<>();
            List<Integer> rightM = new ArrayList<>();
            for (int i = pre;i < s.length();i ++) {
                if ('[' == s.charAt(i)) {
                    leftM.add(i);
                }
                if (']' == s.charAt(i)) {
                    rightM.add(i);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s.substring(0,pre));
            int start = pre;
            for (int i = 0;i < leftM.size();i ++) {
                for (int j = 0;j < Integer.parseInt(s.substring(start,leftM.get(i)));j ++) {
                    stringBuilder.append(s.substring(leftM.get(i) + 1,rightM.get(i)));
                }
                start = rightM.get(i) + 1;
            }
            stringBuilder.append(s.substring(start));
            return stringBuilder.toString();
        }
        //存在嵌套关系，因为是递归，所以每层只关心一层嵌套。
        List<Integer> subLeft = new ArrayList<>();
        List<Integer> subRight = new ArrayList<>();
        StringBuilder result = new StringBuilder(s);
        int col = 0;
        boolean cal = false;
        for (int i = 1;i < s.length();i ++) {
            if (subLeft.size() < 1 && (Character.isLetter(s.charAt(i - 1)) || '[' == s.charAt(i - 1)) && Character.isDigit(s.charAt(i))) {
                subLeft.add(i);
                cal = true;
            }
            if (cal) {
                if ('[' == s.charAt(i)) {
                    col ++;
                }
                if (']' == s.charAt(i)) {
                    col --;
                    if (0 == col) {
                        subRight.add(i);
                        cal = false;
                    }
                }
            }
        }
        for (int i = 0;i < subLeft.size();i ++) {
            result.replace(subLeft.get(i),subRight.get(i) + 1,decodeString(s.substring(subLeft.get(i),subRight.get(i) + 1)));
        }
        return decodeString(result.toString());
    }
}
