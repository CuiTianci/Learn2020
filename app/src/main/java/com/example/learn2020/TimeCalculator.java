package com.example.learn2020;

public class TimeCalculator {
    private long prev = -1L;
    private TimeCalculator() {}

    private static TimeCalculator instance;
    public static TimeCalculator getInstance() {
        if (null == instance) instance = new TimeCalculator();
        return instance;
    }

    public void record() {
        prev = System.currentTimeMillis();
        System.out.println("开始...");
    }

    public void showTime() {
        if (prev < 0) {
            System.out.println("还没有记录开始时间");
            return;
        }
        System.out.println("执行用时(s)：" + (System.currentTimeMillis() - prev) / 1000 );
    }
}
