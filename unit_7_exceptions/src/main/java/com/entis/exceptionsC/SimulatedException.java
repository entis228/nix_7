package com.entis.exceptionsC;

public class SimulatedException extends Exception {

    private static final String message = "hey dude something is wrong... ";

    public SimulatedException(String ex) {
        super(message + ex);
    }
}