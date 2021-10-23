package com.entis.service;

import com.entis.threads.HelloPrinter;

public class ReverseOutputThreads {

    public void print() {
        new Thread(new HelloPrinter()).start();
    }
}
