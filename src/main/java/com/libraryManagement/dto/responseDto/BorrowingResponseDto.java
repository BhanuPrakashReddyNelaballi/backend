package com.libraryManagement.dto.responseDto;

public class BorrowingResponseDto {
    private Long transactionId;
    private String message;

    public BorrowingResponseDto() {}

    public BorrowingResponseDto(Long transactionId, String message) {
        this.transactionId = transactionId;
        this.message = message;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}