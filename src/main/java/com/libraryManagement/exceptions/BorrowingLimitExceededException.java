package com.libraryManagement.exceptions;

public class BorrowingLimitExceededException extends RuntimeException {
    public BorrowingLimitExceededException(String message) {
        super(message);
    }
}
