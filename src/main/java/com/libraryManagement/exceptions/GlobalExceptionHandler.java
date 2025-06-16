package com.libraryManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

    }
    @ExceptionHandler(EmailUpdateNotAllowedException.class)
    public ResponseEntity<String> handleEmailUpdateException(EmailUpdateNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<String> handleBookNotAvailableException(BookNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(BorrowingLimitExceededException.class)
    public ResponseEntity<String> handleBorrowLimitException(BorrowingLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<String> handleInvalidTransaction(InvalidTransactionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
