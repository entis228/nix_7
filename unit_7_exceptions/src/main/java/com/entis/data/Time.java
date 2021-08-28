package com.entis.data;

public abstract class Time {

    public long time;
    public long milliseconds;
    public long seconds;
    public long minutes;
    public long hours;
    public long days = 1;
    public long months = 1;
    public long years;

    public abstract boolean isLeapYear(long years);

    public abstract long daysInYear(long years);

    public abstract long getTime();

    public abstract long getMonthsFromMillis(long millis);

    public abstract long getYearsFromMillis(long millis);

    public abstract long getDaysFromMillis(long millis);

    public abstract long getHoursFromMillis(long millis);

    public abstract long getMinutesFromMillis(long millis);

    public abstract long getSecondsFromMillis(long millis);

    public abstract long getMillisecondsFromTime(long timeMillis);

    public abstract long daysInMonth(long numberOfMonth, long year);

    public abstract void addMilliseconds(long millis);

    public abstract void subtractMilliseconds(long millis);
}
