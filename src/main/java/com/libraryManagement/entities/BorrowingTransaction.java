package com.libraryManagement.entities;

import com.libraryManagement.enums.TransactionStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BorrowingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public BorrowingTransaction() {}

    public BorrowingTransaction(Book book, Member member, LocalDate borrowDate, LocalDate returnDate, TransactionStatus status) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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
