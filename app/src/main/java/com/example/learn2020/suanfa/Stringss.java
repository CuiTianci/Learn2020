package com.example.learn2020.suanfa;

public class Stringss {

    public static void main(String[] args) {
        System.out.println(replaceSpace("We are happy."));
    }

    public static String replaceSpace(String s) {
        char[] original = s.toCharArray();
        int countOfBlank = 0;
        for (char c : original) {
            if (c == ' ')
                countOfBlank++;
        }
        char[] newArr = new char[original.length + countOfBlank * 2];
        int indexOfNew = newArr.length - 1;
        int indexOfOri = original.length - 1;
        while (indexOfOri >= 0 && indexOfNew > indexOfOri) {
            if(original[indexOfOri] == ' ') {
                newArr[indexOfNew --] = '0';
                newArr[indexOfNew --] = '2';
                newArr[indexOfNew --] = '%';
            } else {
                newArr[indexOfNew --] = original[indexOfOri];
            }
            indexOfOri--;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : newArr) {
            sb.append(c);
        }
        for (int i = indexOfOri;i >= 0;i ++) {

        }
        return sb.toString();
    }
}
