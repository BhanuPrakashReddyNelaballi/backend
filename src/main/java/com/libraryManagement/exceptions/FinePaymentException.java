package com.libraryManagement.exceptions;

public class FinePaymentException extends RuntimeException {
    public FinePaymentException(String message) {
        super(message);
    }
}