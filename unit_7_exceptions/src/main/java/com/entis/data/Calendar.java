package com.entis.data;

import com.entis.exceptionsC.SimulatedException;

import java.io.Serializable;

public class Calendar extends Time implements Serializable {

    private static final int millisInSecond = 1000;
    private static final int secondsInMinute = 60;
    private static final int minutesInHour = 60;
    private static final int hoursInDay = 24;
    private static final int daysInYear = 365;
    private static final int daysInLeapYear = 366;
    private static final int monthInYear = 12;
    private static final long millisInDay = hoursInDay * minutesInHour * secondsInMinute * millisInSecond;
    static private final long millisInYear = millisInDay * daysInYear;
    static private final long millisInLeapYear = millisInDay * daysInLeapYear;

    public Calendar() {
    }

    public Calendar(long millis, long second, long minute, long hour, long day, long month,
                    long year) {
        super.milliseconds = millis;
        super.seconds = second;
        super.minutes = minute;
        super.hours = hour;
        super.days = day;
        if (month == 0) {
            System.out.println("All month must starting with 1");
            super.months = 1;
        } else {
            super.months = month;
        }
        super.years = year;
        super.time = millis + (second * millisInSecond) + (minute * millisInSecond * secondsInMinute) + (hour * millisInSecond * secondsInMinute * minutesInHour)
                + (day * millisInDay) + (getMillisecondsInMonths(month, year))
                + (getMillisecondsInYear(year));
    }

    public Calendar(long milliseconds) {
        if (milliseconds < 0) {
            try {
                throw new SimulatedException("Exception: milliseconds cant be < 0");
            } catch (SimulatedException e) {
                e.printStackTrace();
            }
        }
        super.time = milliseconds;
        super.milliseconds = getMillisecondsFromTime(milliseconds);
        super.seconds = getSecondsFromMillis(milliseconds);
        super.minutes = getMinutesFromMillis(milliseconds);
        super.hours = getHoursFromMillis(milliseconds);
        super.days = getDaysFromMillis(milliseconds);
        super.months = getMonthsFromMillis(milliseconds);
        super.years = getYearsFromMillis(milliseconds);
    }

    private long getMillisecondsInYear(long year) {
        long time = 0;
        for (int i = 0; i < year; i++) {
            long test = daysInYear(i) * millisInDay;
            time = time + (test);
        }
        for (int i = 1; i <= year / 100; i++) {
            if (year >= ((100 * i) + 1)) {
                time += millisInDay;
            }
            if (year >= ((400 * i) + 1)) {
                time -= millisInDay;
            }
        }
        return time;
    }

    public long getMillisecondsInMonths(long month, long year) {
        long time = 0;
        if (month == 1) {
            return 0;
        }
        for (int i = 0; i < month; i++) {
            time = time + (daysInMonth(i, year) * millisInDay);
        }
        return time;
    }

    @Override
    public boolean isLeapYear(long years) {
        return (years % 400 == 0) || ((years % 4 == 0) && (years % 100 != 0));
    }

    @Override
    public long daysInYear(long year) {
        if (isLeapYear(year)) {
            return 366;
        }
        return 365;
    }

    @Override
    public long getTime() {
        return super.time;
    }

    @Override
    public long getMonthsFromMillis(long millis) {
        long numberOfMonth;
        long days = millis / millisInSecond / secondsInMinute / minutesInHour / hoursInDay;
        numberOfMonth = 1;
        final long currentMillis = getYearsFromMillis(millis);
        long daysInThisYear = days;
        for (int i = 0; i < currentMillis; i++) {
            daysInThisYear = daysInThisYear - (daysInYear(i));
        }
        long nowYear = getYearsFromMillis(millis);
        for (int i = 0; i < 12; i++) {
            if (daysInThisYear >= daysInMonth(i + 1, nowYear)) {
                numberOfMonth++;
                daysInThisYear = daysInThisYear - daysInMonth(i + 1, nowYear);
            } else break;
        }
        return numberOfMonth;
    }

    @Override
    public long getYearsFromMillis(long millis) {
        long numberOfYears = 0;
        int count = 0;
        long days = millis / hoursInDay / minutesInHour / secondsInMinute / millisInSecond;
        while (true) {
            if (days >= 365) {
                switch (++count) {
                    case 1:
                    case 2:
                    case 3: {
                        numberOfYears++;
                        days = days - 365;
                        break;
                    }
                    case 4: {
                        numberOfYears++;
                        days = days - 366;
                        count = 0;
                        break;
                    }
                }
            } else {
                break;
            }
        }
        return numberOfYears;
    }

    @Override
    public long getDaysFromMillis(long milliseconds) {
        int fourYears = (daysInYear * 3 + daysInLeapYear);
        long daysInThisYear = ((milliseconds / millisInSecond / secondsInMinute / minutesInHour / hoursInDay) % fourYears) % 365;
        long nowYear = getYearsFromMillis(milliseconds);
        for (int i = 0; i < 12; i++) {
            if (daysInThisYear >= daysInMonth(i + 1, nowYear)) {
                daysInThisYear = daysInThisYear - daysInMonth(i + 1, nowYear);
            } else {
                break;
            }
        }
        return daysInThisYear;
    }

    @Override
    public long getHoursFromMillis(long milliseconds) {
        return (milliseconds % millisInDay) / millisInSecond / secondsInMinute / minutesInHour;
    }

    @Override
    public long getMinutesFromMillis(long milliseconds) {
        return (milliseconds % (millisInSecond * secondsInMinute * minutesInHour)) / millisInSecond / secondsInMinute;
    }

    @Override
    public long getSecondsFromMillis(long milliseconds) {
        return (milliseconds % (millisInSecond * secondsInMinute)) / millisInSecond;
    }

    @Override
    public long getMillisecondsFromTime(long time) {
        return (time % millisInSecond);
    }

    @Override
    public long daysInMonth(long numberOfMonth, long years) {
        return (long) (
                28 + ((numberOfMonth + Math.floor(numberOfMonth / 8)) % 2) + 2 % numberOfMonth + Math
                        .floor((1 + (1 - (years % 4 + 2) % (years % 4 + 1)) * ((years % 100 + 2) % (years % 100 + 1))
                                + (1 - (years % 400 + 2) % (years % 400 + 1))) / numberOfMonth) + Math.floor(1 / numberOfMonth)
                        - Math.floor(((1 - (years % 4 + 2) % (years % 4 + 1)) * ((years % 100 + 2) % (years % 100 + 1))
                        + (1 - (years % 400 + 2) % (years % 400 + 1))) / numberOfMonth));
    }

    @Override
    public void addMilliseconds(long milliseconds) {
        super.time = time + milliseconds;
        super.milliseconds = getMillisecondsFromTime(super.time);
        super.seconds = getSecondsFromMillis(super.time);
        super.minutes = getMinutesFromMillis(super.time);
        super.hours = getHoursFromMillis(super.time);
        super.days = getDaysFromMillis(super.time);
        super.months = getMonthsFromMillis(super.time);
        super.years = getYearsFromMillis(super.time);
    }

    @Override
    public void subtractMilliseconds(long milliseconds) {
        super.time = time - milliseconds;
        super.milliseconds = getMillisecondsFromTime(super.time);
        super.seconds = getSecondsFromMillis(super.time);
        super.minutes = getMinutesFromMillis(super.time);
        super.hours = getHoursFromMillis(super.time);
        super.days = getDaysFromMillis(super.time);
        super.months = getMonthsFromMillis(super.time);
        super.years = getYearsFromMillis(super.time);
    }

    public void print(Calendar calendar) {
        System.out.print(calendar.days + " |");
        switch ((int) calendar.months) {
            case 1: {
                System.out.print("  Январь January     |");
            }
            break;
            case 2: {
                System.out.print("  Февраль February   |");
            }
            break;
            case 3: {
                System.out.print("  Март March         |");
            }
            break;
            case 4: {
                System.out.print("  Апрель April       |");
            }
            break;
            case 5: {
                System.out.print("  Май May            |");
            }
            break;
            case 6: {
                System.out.print("  Июнь June          |");
            }
            break;
            case 7: {
                System.out.print("  Июль July          |");
            }
            break;
            case 8: {
                System.out.print("  Август August      |");
            }
            break;
            case 9: {
                System.out.print("  Сентябрь September |");
            }
            break;
            case 10: {
                System.out.print("   Октябрь October   |");
            }
            break;
            case 11: {
                System.out.print("   Ноябрь November |");
            }
            break;
            case 12: {
                System.out.print("  Декабрь December |");
            }
            break;
        }
        System.out.printf(" %04d ", calendar.years);
        System.out.print(calendar.hours + " : ");
        System.out.print(calendar.minutes + " : ");
        System.out.print(calendar.seconds + " : ");
        System.out.print(calendar.milliseconds);
        System.out.println();
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "time=" + time +
                ", milliseconds=" + milliseconds +
                ", seconds=" + seconds +
                ", minutes=" + minutes +
                ", hours=" + hours +
                ", days=" + days +
                ", months=" + months +
                ", years=" + years +
                '}';
    }
}
