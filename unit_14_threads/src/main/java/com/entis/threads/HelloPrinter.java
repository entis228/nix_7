package com.entis.threads;

public class HelloPrinter implements Runnable {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        if (!threadName.endsWith("49")) {
            try {
                Thread newThread = new Thread(new HelloPrinter());
                newThread.start();
                newThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hello from " + threadName);
    }
}
