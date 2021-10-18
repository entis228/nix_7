package com.entis.exception;

public class IncorrectDateException extends RuntimeException {

    public IncorrectDateException() {
        super("Account incorrect input date");
    }
}
