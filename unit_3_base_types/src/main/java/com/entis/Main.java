package com.entis;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        new First().doTask();
        new Second().doTask();
        new Third().doTask();
        System.out.println("Thank you");
        Thread.sleep(1000);
    }
}
