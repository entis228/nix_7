package com.entis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class First {
    public void doTask() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("First task. Enter your string");
        String input = reader.readLine();
        String[]numbers=input.split("\\D+");
        AtomicLong sum = new AtomicLong();
        Arrays.stream(numbers).filter(x->!x.equals("")).forEach(x-> sum.addAndGet(Long.parseLong(x)));
        System.out.println("Sum of all numbers is "+sum);
    }
}
