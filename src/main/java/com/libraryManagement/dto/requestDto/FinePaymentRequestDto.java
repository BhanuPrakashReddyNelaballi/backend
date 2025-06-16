package com.libraryManagement.dto.requestDto;

public class FinePaymentRequestDto {
    private int fineId;

    public FinePaymentRequestDto() {}

    public FinePaymentRequestDto(int fineId) {
        this.fineId = fineId;
    }

    public int getFineId() { return fineId; }
    public void setFineId(int fineId) { this.fineId = fineId; }
}