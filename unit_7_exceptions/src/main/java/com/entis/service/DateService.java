package com.entis.service;

import com.entis.entity.Date;
import com.entis.entity.TimeType;
import com.entis.util.DateUtils;

import java.util.List;

public class DateService {

    private static final int millisInSecond=1000;
    private static final int secondsInMinute=60;
    private static final int minutesInHour=60;
    private static final int hoursInDay=24;
    private static final int daysInYear=365;
    private static final int daysInLeapYear=366;
    private static final int monthInYear=12;
    private static final long millisInDay=hoursInDay*minutesInHour*secondsInMinute*millisInSecond;

    private long getSeconds(Date date){
        return date.getTime()/millisInSecond;
    }

    private long getMinutes(Date date){
        return date.getTime()/millisInSecond/secondsInMinute;
    }

    private long getHours(Date date){
        return date.getTime()/millisInSecond/secondsInMinute/
                minutesInHour;
    }

    private long getDays(Date date){
        return date.getTime()/millisInSecond/secondsInMinute/
                minutesInHour/hoursInDay;
    }

    private int getYears(Date date){
        long days=getDays(date);
        int result=0;
        while (days>0){
            if(DateUtils.isLeapYear(result)){
                days-=daysInLeapYear;
            }else days-=daysInYear;
            result++;
        }
        return result;
    }

    private long getMonths(Date date){
        return ((long) getYears(date) *monthInYear);
    }

    public String difference(Date data1, Date data2, TimeType type) {
        Date differenceResult=new Date();
        differenceResult.setTime(Math.abs(data1.getTime()-data2.getTime()));
        long result=-1;
        switch (type) {
            case MILLIS:
                result = differenceResult.getTime();
                break;
            case SECONDS:
                result = getSeconds(differenceResult);
                break;
            case MINUTES:
                result=getMinutes(differenceResult);
                break;
            case HOURS:
                result=getHours(differenceResult);
                break;
            case DAY:
                result=getDays(differenceResult);
                break;
            case MONTH:
                result=getMonths(differenceResult);
                break;
            case YEARS:
                result=getYears(differenceResult);
                break;
        }
        if(result==-1)
            throw new RuntimeException("some problem");
        return Long.toString(result);
    }

    public Date addToDate(Date date, long count, TimeType type) {
        Date result=new Date();
        switch (type) {
            case MILLIS:
                result.setTime(date.getTime()+count);
                break;
            case SECONDS:
                result.setTime(date.getTime()+count*millisInSecond);
                break;
            case MINUTES:
                result.setTime(date.getTime()+count*millisInSecond*secondsInMinute);
                break;
            case HOURS:
                result.setTime(date.getTime()+count*millisInSecond*secondsInMinute*minutesInHour);
                break;
            case DAY:
                result.setTime(date.getTime()+count*millisInDay);
                break;
            case MONTH:
                result.setTime(date.getTime());
                for(int i=0;i<count;i++){

                }
                break;
            case YEARS:
                result.setTime(date.getTime());
                for(int i=0;i<count;i++){
                    if(DateUtils.isLeapYear(getYears(result)+1)){
                        result.setTime(result.getTime()+daysInLeapYear *millisInDay);
                    }else result.setTime(result.getTime()+daysInYear*millisInDay);
                }
                break;
        }
        return result;
    }

    public void subtractDate(Date date, Date subtraction) {
    }

    public void sortDates(List<Date> dates, boolean isAscending) {
    }
}
