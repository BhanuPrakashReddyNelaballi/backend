package com.libraryManagement.exceptions;

public class EmailUpdateNotAllowedException extends RuntimeException {
    public EmailUpdateNotAllowedException(String message)
    {
      super(message);
    }
}
