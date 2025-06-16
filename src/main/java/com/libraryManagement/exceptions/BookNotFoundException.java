package com.libraryManagement.exceptions;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String msg){
        super(msg);
    }
}
