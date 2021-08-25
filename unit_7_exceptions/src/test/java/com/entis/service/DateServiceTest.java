//package com.entis.service;
//
//import com.entis.entity.Date;
//import com.entis.entity.TimeType;
//import com.entis.util.CalendarUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.time.temporal.ChronoUnit;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class DateServiceTest {
//
//    private static final DateService service = new DateService();
//
//    @Test
//    public void difference() {
//        for(int i=0;i<10;i++) {
//            LocalDateTime fromDateTime = LocalDateTime.of(CalendarUtils.getRandomNumber(0,2500), CalendarUtils.getRandomNumber(1,12), CalendarUtils.getRandomNumber(1,28),
//                    CalendarUtils.getRandomNumber(0,23), CalendarUtils.getRandomNumber(0,59), CalendarUtils.getRandomNumber(0,59));
//            LocalDateTime toDateTime = LocalDateTime.of(2020, 5, 18, 6, 44, 45);
//            Date fromDate = new Date();
//            Date toDate = new Date();
//            fromDate.setTime(fromDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() + CalendarUtils.millisTo1970());
//            toDate.setTime(toDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() + CalendarUtils.millisTo1970());
//            LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
//            long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
//            years=Math.abs(years);
//            Assertions.assertEquals(service.difference(fromDate, toDate, TimeType.YEARS), Long.toString(years));
//            long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
//            months=Math.abs(months);
//            Assertions.assertEquals(service.difference(fromDate, toDate, TimeType.MONTH), Long.toString(months));
//            long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
//            days=Math.abs(days);
//            Assertions.assertEquals(service.difference(fromDate, toDate, TimeType.DAY), Long.toString(days));
//            long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
//            hours=Math.abs(hours);
//            Assertions.assertEquals(service.difference(fromDate, toDate, TimeType.HOURS), Long.toString(hours));
//            long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
//            minutes=Math.abs(minutes);
//            Assertions.assertEquals(service.difference(fromDate, toDate, TimeType.MINUTES), Long.toString(minutes));
//            long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);
//            seconds=Math.abs(seconds);
//            Assertions.assertEquals(service.difference(fromDate, toDate, TimeType.SECONDS), Long.toString(seconds));
//            long millis = tempDateTime.until(toDateTime, ChronoUnit.MILLIS);
//            millis=Math.abs(millis);
//            Assertions.assertEquals(service.difference(fromDate, toDate, TimeType.MILLIS), Long.toString(millis));
//        }
//    }
//}
