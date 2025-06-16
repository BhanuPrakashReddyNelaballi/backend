package com.libraryManagement.entities;

import com.libraryManagement.enums.FineStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fineID;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private double amount;

    @Enumerated(EnumType.STRING)
    private FineStatus status;

    private LocalDate transactionDate;
    private LocalDate lastPaymentDate;

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    // Getters and setters
    public int getFineID() { return fineID; }
    public void setFineID(int fineID) { this.fineID = fineID; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public FineStatus getStatus() { return status; }
    public void setStatus(FineStatus status) { this.status = status; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
}