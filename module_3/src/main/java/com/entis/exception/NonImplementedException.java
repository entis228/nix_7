package com.entis.exception;

public class NonImplementedException extends RuntimeException{

    public NonImplementedException() {
        super("Method not implemented");
    }
}
