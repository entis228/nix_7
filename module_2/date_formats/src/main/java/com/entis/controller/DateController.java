package com.entis.controller;

import com.entis.service.DateService;
import com.entis.service.DateServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DateController {

    private static final DateService dateService = new DateServiceImpl();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    void dateFormatConvert() {
        try {
            System.out.println("Enter the date in such formats:");
            System.out.println("year/month/day, day/month/year, month-day-year");
            System.out.println("Year must contains 4 numbers (e.g 0003, 0440, 2001)");
            System.out.println("Day and month must contains 2 numbers (e.g 03, 09, 11)");
            System.out.println("Enter stop if you want to stop entering dates");
            while (true) {
                String input = reader.readLine();
                if (input.equals("stop")) break;
                dateService.inputDateCheck(input);
            }
            dateService.getList().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
