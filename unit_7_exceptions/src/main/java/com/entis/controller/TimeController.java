package com.entis.controller;

import com.entis.data.Calendar;
import com.entis.data.Time;
import com.entis.service.Parser;
import com.entis.util.TimeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class TimeController {

    private static final int millisInSecond = 1000;
    private static final int secondsInMinute = 60;
    private static final int minutesInHour = 60;
    private static final int hoursInDay = 24;
    private static final int daysInYear = 365;
    private static final int daysInLeapYear = 366;
    private static final long millisInDay = hoursInDay * minutesInHour * secondsInMinute * millisInSecond;

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String currentFormat = "dd/mm/yy hh:mm:ss:msmsms";

    public void setFormat() {
        String choice = "1";
        System.out.println("Select the format");
        System.out.println("1- dd/mm/yy hh:mm:ss:msmsms");
        System.out.println("2- mm/dd/yy hh:mm:ss:msmsms");
        System.out.println("3- mmm/dd/yy hh:mm:ss:msmsms");
        System.out.println("4- dd/mmm/yy hh:mm:ss:msmsms");
        try {
            choice = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input");
        }
        switch (choice) {
            case "1":
                currentFormat = "dd/mm/yy hh:mm:ss:msmsms";
                break;
            case "2":
                currentFormat = "mm/dd/yy hh:mm:ss:msmsms";
                break;
            case "3":
                currentFormat = "mmm/dd/yy hh:mm:ss:msmsms";
                break;
            case "4":
                currentFormat = "dd/mmm/yy hh:mm:ss:msmsms";
                break;
            default:
                System.out.println("Incorrect choice");
        }
    }

    public void differenceInDates() {
        System.out.println("Enter the first date (e.g. 09-10-2008 00:00:55:777). Print 1 if you want to use date in example");
        System.out.println("Print 2 if you want to enter custom date");
        String selectedFirstDate = "09-10-2008 00:00:55:777 ";
        try {
            String choice = reader.readLine();
            if (!(choice.equals("1"))) {
                System.out.println("Enter first date:");
                selectedFirstDate = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Enter the second date (e.g 05-07-2004 01:10:55:777) Print 1 if you want to use date in example");
        System.out.println("Print 2 if you want to enter custom date");
        String selectedSecondDate = "05-07-2004 01:10:55:777 ";
        try {
            String select = reader.readLine();
            if (!(select.equals("1"))) {
                System.out.println("Enter the second date:");
                selectedSecondDate = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Time timeStart = Parser.getInstance()
                .ParseDayMonthYearHourEtcToCalendarDateFormat(selectedFirstDate, currentFormat);
        Time timeStop = Parser.getInstance()
                .ParseDayMonthYearHourEtcToCalendarDateFormat(selectedSecondDate, currentFormat);
        Time differenceBetweenDates = new TimeUtils().findDifferenceBetweenDates(timeStart, timeStop);
        System.out.println("Select type of result:");
        System.out.println("1-milliseconds");
        System.out.println("2-seconds");
        System.out.println("3-minutes");
        System.out.println("4-hours");
        System.out.println("5-days");
        System.out.println("6-months");
        System.out.println("7-years");
        try {
            String answer = reader.readLine();
            switch (answer) {
                case "1":
                    System.out.println(differenceBetweenDates.getTime() + " milliseconds");
                    break;
                case "2":
                    System.out.println(differenceBetweenDates.getTime() / millisInSecond + " seconds");
                    break;
                case "3":
                    System.out.println(differenceBetweenDates.getTime() / millisInSecond / secondsInMinute + " minutes");
                    break;
                case "4":
                    System.out.println(differenceBetweenDates.getTime() / millisInSecond / secondsInMinute / minutesInHour + " hours");
                    break;
                case "5":
                    System.out.println(differenceBetweenDates.getTime() / millisInSecond / secondsInMinute / minutesInHour / hoursInDay + " days");
                    break;
                case "6":
                    System.out.println(new Calendar().getMonthsFromMillis(differenceBetweenDates.getTime()) + " months");
                    break;
                case "7":
                    System.out.println(new Calendar().getYearsFromMillis(differenceBetweenDates.getTime()) + " years");
                    break;
                default:
                    System.out.println("Incorrect choice!");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTimeToDate() {
        System.out.println("Enter the date or time with text (e.g. text 01-03-2002 text 00:00:55:777 text)");
        System.out.println("Print 1 if you want to use date in example");
        System.out.println("Print 2 if you want to enter the custom date");
        String firstDate = "text 01-03-2002 text 00:00:55:777 text";
        try {
            String select = reader.readLine();
            if (!(select.equals("1"))) {
                System.out.println("Enter date:");
                firstDate = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Enter the date (e.g 04-02-2007 07:05:00:000 )");
        System.out.println("Print 1 if you want to use date in example");
        System.out.println("Print any if you want to enter the custom date");
        String secondDate = " 04-02-2007  07:05:00:000 ";
        try {
            String choice = reader.readLine();
            if (!(choice.equals("1"))) {
                System.out.println("Enter add date:");
                secondDate = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Time timeStart = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(firstDate, currentFormat);
        Time timeStop = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(secondDate, currentFormat);
        final Time result = new TimeUtils().timeAddTime(timeStart, timeStop);
        System.out.println("================================================");
        new Calendar().print((Calendar) timeStart);
        new Calendar().print((Calendar) timeStop);
        new Calendar().print((Calendar) result);
        System.out.println("Days in the first date: " + timeStart.getTime() / millisInDay +
                "\nDays in the second date: " + timeStop.getTime() / millisInDay +
                "\nResult(days): " + result.getTime() / millisInDay);
        System.out.println("================================================");
    }

    public void subtract() {
        System.out.println("Enter the date with text (e.g text 04-07-2008 text 03:05:55:777 text)");
        System.out.println("Print 1 if you want to use date in example");
        System.out.println("Print 2 if you want to enter the custom date");
        String firstDate = "text 04-07-2008 text 03:05:55:777 text";
        try {
            String select = reader.readLine();
            if (!(select.equals("1"))) {
                System.out.println("Enter the date:");
                firstDate = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Enter the date with text to subtract  (e.g text 01-02-0008 text 01:01:01:111 Some text)");
        System.out.println("Print 1 if you want to use date in example");
        System.out.println("Print 2 if you want to enter the custom date");
        String secondDate = "text 01-01-0008 text 01:02:01:111 text";
        try {
            String select = reader.readLine();
            if (!(select.equals("1"))) {
                System.out.println("Enter next date:");
                secondDate = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Time timeStart = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(firstDate, currentFormat);
        Time timeStop = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(secondDate, currentFormat);
        final Time result = new TimeUtils().timeSubtractTime(timeStart, timeStop);
        System.out.println("_______________________________________________");
        new Calendar().print((Calendar) timeStart);
        new Calendar().print((Calendar) timeStop);
        new Calendar().print((Calendar) result);
        System.out.println("Days in the first date: " + timeStart.getTime() / millisInDay +
                "\nDays in the second date: " + timeStop.getTime() / millisInDay +
                "\nResult(days): " + result.getTime() / millisInDay);
        System.out.println("_______________________________________________");
    }

    public void sortTimesFromHighToLow() {
        System.out.println("Select:");
        System.out.println("Print 1 if you want to enter dates");
        System.out.println("Print 2 if you want auto generated dates");
        Time[] times = new Time[1];
        try {
            String select = reader.readLine();
            if (select.equals("1")) {
                System.out.println("Enter the date and time (e.g 04-07-2008 03:05:55:777)");
                boolean isNextDate = true;
                int dateTempCounter = 0;
                times[dateTempCounter] = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(reader.readLine(), currentFormat);
                while (isNextDate) {
                    dateTempCounter++;
                    System.out.println("Enter the next date and time (e.g 04-07-2008 03:05:55:777)");
                    times = Arrays.copyOf(times, dateTempCounter + 1);
                    times[dateTempCounter] = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(
                            reader.readLine(), currentFormat);
                    System.out.println("Enter 1 to stop");
                    if (reader.readLine().equals("1")) {
                        isNextDate = false;
                    }
                }
            }
            if (select.equals("2")) {
                System.out.println("Enter amount of dates:");
                final char[] chars = reader.readLine().toCharArray();
                StringBuilder result = new StringBuilder();
                for (char aChar : chars) {
                    if (String.valueOf(aChar).matches("\\d")) {
                        result.append(aChar);
                    }
                }
                int numbers = Integer.parseInt(result.toString());
                for (int i = 0; i < numbers; i++) {
                    int dd = (new Random().nextInt((31) + 1));
                    String mm = String.format("%02d", (new Random().nextInt((12 - 2) + 1) + 2));
                    int yyyy = (new Random().nextInt((3000 - 1) + 1) + 1);
                    int hh = (new Random().nextInt((23) + 1));
                    int mi = (new Random().nextInt((59) + 1));
                    int ss = (new Random().nextInt((59) + 1));
                    int msm = (new Random().nextInt((999) + 1));
                    times = Arrays.copyOf(times, i + 1);
                    times[i] = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(
                            dd + "-" + mm + "-" + yyyy + " " + hh + ":" + mi + ":" + ss + ":" + msm, currentFormat);
                }
            }
            final Time[] timesSorted = new TimeUtils().sortTimesFromHighToLow(times);
            for (Time time : timesSorted) {
                new Calendar().print((Calendar) time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortTimesFromLowToHigh() {
        System.out.println("Select:");
        System.out.println("Print 1 if you want to enter dates");
        System.out.println("Print 2 if you want auto generated dates");
        Time[] times = new Time[1];
        try {
            String select = reader.readLine();
            if (select.equals("1")) {
                System.out.println("Enter the date and time (e.g 04-07-2008 03:05:55:777)");
                boolean isNextDate = true;
                int dateTempCounter = 0;
                times[dateTempCounter] = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(reader.readLine(), currentFormat);
                while (isNextDate) {
                    dateTempCounter++;
                    System.out.println("Enter the next date and time (e.g 04-07-2008 03:05:55:777)");
                    times = Arrays.copyOf(times, dateTempCounter + 1);
                    times[dateTempCounter] = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(
                            reader.readLine(), currentFormat);
                    System.out.println("Enter 1 to stop");
                    if (reader.readLine().equals("1")) {
                        isNextDate = false;
                    }
                }
            }
            if (select.equals("2")) {
                System.out.println("Enter amount of dates:");
                final char[] chars = reader.readLine().toCharArray();
                StringBuilder result = new StringBuilder();
                for (char aChar : chars) {
                    if (String.valueOf(aChar).matches("\\d")) {
                        result.append(aChar);
                    }
                }
                int numbers = Integer.parseInt(result.toString());
                for (int i = 0; i < numbers; i++) {
                    int dd = (new Random().nextInt((31) + 1));
                    String mm = String.format("%02d", (new Random().nextInt((12 - 2) + 1) + 2));
                    int yyyy = (new Random().nextInt((3000 - 1) + 1) + 1);
                    int hh = (new Random().nextInt((23) + 1));
                    int mi = (new Random().nextInt((59) + 1));
                    int ss = (new Random().nextInt((59) + 1));
                    int msm = (new Random().nextInt((999) + 1));
                    times = Arrays.copyOf(times, i + 1);
                    times[i] = Parser.getInstance().ParseDayMonthYearHourEtcToCalendarDateFormat(
                            dd + "-" + mm + "-" + yyyy + " " + hh + ":" + mi + ":" + ss + ":" + msm, currentFormat);
                }
            }
            final Time[] timesSorted = new TimeUtils().sortTimesFromLowToHigh(times);
            for (Time time : timesSorted) {
                new Calendar().print((Calendar) time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
