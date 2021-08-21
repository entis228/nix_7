package com.entis.util;

public class CalendarUtils {

    public static int getRandomNumber(long from, long to) {
        return (int) (Math.random() * (to - from + 1) + from);
    }
}
