package com.libraryManagement.exceptions;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}
