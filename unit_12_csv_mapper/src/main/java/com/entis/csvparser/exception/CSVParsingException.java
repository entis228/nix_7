package com.entis.csvparser.exception;

public class CSVParsingException extends Exception {

    public CSVParsingException(String message) {
        super(message);
    }

    public CSVParsingException(Throwable cause) {
        super(cause);
    }
}
