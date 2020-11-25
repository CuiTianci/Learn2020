package com.example.learn2020;

public class Runnable {

    public static void main(String[] args) {
        System.out.println(getDaysPast(1597274755));
        System.out.println(getDaysPast(1598057520));
        System.out.println(getDaysPast(1598086320));
        System.out.println(getDaysPast(1598093520));
        System.out.println(getDaysPast(1598122320));
    }

    public static int getDaysPast(long timestamp) {
        return (int) (timestamp + 8 * 60 * 60) / (60 * 60 * 24);
    }
}
