package com.libraryManagement.dto.requestDto;

public class ReturnRequestDto {
    private Long transactionId;

    public ReturnRequestDto() {
    }

    public ReturnRequestDto(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

}