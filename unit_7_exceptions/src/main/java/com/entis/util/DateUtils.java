package com.entis.util;

public class DateUtils {

    public static boolean isLeapYear(long year) {
        return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
    }
}
