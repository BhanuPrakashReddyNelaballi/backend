package com.libraryManagement.dto.responseDto;

import com.libraryManagement.enums.TransactionStatus;

import java.time.LocalDate;

public class BorrowingDto {
    private long transactionId;
    private long bookId;
    private long memberId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private TransactionStatus status;

    public BorrowingDto() {
    }

    public BorrowingDto(long transactionId, long bookId, long memberId, LocalDate borrowDate, LocalDate returnDate, TransactionStatus status) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
