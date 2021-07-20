package com.entis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Third {
    public void doTask() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int lesson;
        System.out.println("Third task. Enter your number of lesson:");
        lesson = Integer.parseInt(reader.readLine());
        int minutes=lesson*45+(lesson/2)*5+((lesson+1)/2-1)*15;
        System.out.println("Your lesson will end at");
        System.out.printf("%d:%d%n",minutes/60+9,minutes%60);
    }
}
