package com.entis.service;

import com.entis.entity.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DateServiceImpl implements DateService {

    private static final List<String> dates = new ArrayList<>();
    private static final String[] regexes = {
            "\\d{4}/\\d{1,2}/\\d{1,2}",
            "\\d{2}/\\d{2}/\\d{4}",
            "\\d{2}-\\d{2}-\\d{4}"
    };

    public boolean isLeapYear(int year) {
        return (year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0));
    }

    private boolean isDateValid(Date date) {
        int daysInFeb = 28;
        if (isLeapYear(date.getYear())) daysInFeb = 29;
        int[] daysInMonth = {31, daysInFeb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (date.getMonth() <= 12 && date.getMonth() > 0) {
            return date.getDay() > 0 && date.getDay() <= daysInMonth[date.getMonth() - 1];
        }
        return false;
    }

    @Override
    public void inputDateCheck(String input) {
        int regexType = -1;
        for (int i = 0; i < regexes.length; i++) {
            Pattern pattern = Pattern.compile(regexes[i]);
            if (pattern.matcher(input).matches()) {
                regexType = i;
                break;
            }
        }
        Date date = new Date();
        String[] inputParts;
        switch (regexType) {
            case 0:
                inputParts = input.split("/");
                date.setYear(Integer.parseInt(inputParts[0]));
                date.setMonth(Integer.parseInt(inputParts[1]));
                date.setDay(Integer.parseInt(inputParts[2]));
                break;
            case 1:
                inputParts = input.split("/");
                date.setDay(Integer.parseInt(inputParts[0]));
                date.setMonth(Integer.parseInt(inputParts[1]));
                date.setYear(Integer.parseInt(inputParts[2]));
                break;
            case 2:
                inputParts = input.split("-");
                date.setMonth(Integer.parseInt(inputParts[0]));
                date.setDay(Integer.parseInt(inputParts[1]));
                date.setYear(Integer.parseInt(inputParts[2]));
                break;
        }
        if (isDateValid(date)) {
            String resultDay = String.valueOf(date.getDay());
            String resultMonth = String.valueOf(date.getMonth());
            resultDay = resultDay.length() < 2 ? "0" + resultDay : resultDay;
            resultMonth = resultMonth.length() < 2 ? "0" + resultMonth : resultMonth;
            dates.add(date.getYear() + resultMonth + resultDay);
        }
    }

    public List<String> getList() {
        List<String>result=new ArrayList<>(dates);
        dates.clear();
        return result;
    }
}
