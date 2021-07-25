package com.entis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Third {

    private static final int LESSON_DURATION = 45;
    private static final int HOUR_DURATION = 60;
    private static final int EVEN_SCHOOL_BREAK_DURATION = 5;
    private static final int ODD_SCHOOL_BREAK_DURATION = 15;

    public void doTask() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int lesson;
        System.out.println("Third task. Enter your number of lesson:");
        lesson = Integer.parseInt(reader.readLine());
        int minutes = lesson * LESSON_DURATION + (lesson/2) * EVEN_SCHOOL_BREAK_DURATION + ((lesson + 1)/2 - 1) * ODD_SCHOOL_BREAK_DURATION;
        System.out.println("Your lesson will end at");
        System.out.printf("%d:%d%n", minutes/HOUR_DURATION + 9, minutes%HOUR_DURATION);
    }
}
