//package com.entis.util;
//
//public class CalendarUtils {
//
//    private static final int millisInSecond = 1000;
//    private static final int secondsInMinute = 60;
//    private static final int minutesInHour = 60;
//    private static final int hoursInDay = 24;
//    private static final int daysInYear = 365;
//    private static final int daysInLeapYear = 366;
//    private static final int monthInYear = 12;
//    private static final long millisInDay = hoursInDay * minutesInHour * secondsInMinute * millisInSecond;
//    static private final long millisInYear = millisInDay * daysInYear;
//    static private final long millisInLeapYear = millisInDay * daysInLeapYear;
//
//    public static int getRandomNumber(long from, long to) {
//        return (int) (Math.random() * (to - from + 1) + from);
//    }
//
//    public static long millisTo1970() {
//        long result = 0;
//        for (int i = 0; i < 1970; i++) {
//            if (DateUtils.isLeapYear(i)) {
//                result += daysInLeapYear * millisInDay;
//            } else result += daysInYear * millisInDay;
//        }
//        return result;
//    }
//}
