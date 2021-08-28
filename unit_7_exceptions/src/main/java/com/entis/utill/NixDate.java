package com.entis.utill;

import com.entis.data.Calendar;
import com.entis.data.Time;

public class NixDate {

    public Time findDifferenceBetweenDates(Time timeStart, Time timeEnd) {
        return new Calendar(timeStart.time - timeEnd.time);
    }

    public Time timeAddTime(Time timeStart, Time timeEnd) {
        return new Calendar(timeStart.time + timeEnd.time);
    }

    public Time timeSubtractTime(Time timeEnd, Time timeStart) {
        return new Calendar(timeEnd.time - timeStart.time);
    }

    public Time[] sortTimesFromLowToHight(Time[] times) {
        boolean isSorted = false;
        Time buf;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < times.length - 1; i++) {
                if (times[i].getTime() > times[i + 1].getTime()) {
                    isSorted = false;
                    buf = times[i];
                    times[i] = times[i + 1];
                    times[i + 1] = buf;
                }
            }
        }
        return times;
    }

    public Time[] sortTimesFromHightToLow(Time[] times) {
        boolean isSorted = false;
        Time buf;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < times.length - 1; i++) {
                if (times[i].getTime() < times[i + 1].getTime()) {
                    isSorted = false;
                    buf = times[i];
                    times[i] = times[i + 1];
                    times[i + 1] = buf;
                }
            }
        }
        return times;
    }
}
