package com.entis.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountName) {
        super("Account " + accountName + " not found");
    }

    public AccountNotFoundException(String accountName, String username) {
        super("Account " + accountName + " not found in user " + username);
    }
}
