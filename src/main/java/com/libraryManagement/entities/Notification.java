package com.libraryManagement.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationID;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private String message;
    private LocalDate dateSent;

    // Getters and setters
    public int getNotificationID() { return notificationID; }
    public void setNotificationID(int notificationID) { this.notificationID = notificationID; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDate getDateSent() { return dateSent; }
    public void setDateSent(LocalDate dateSent) { this.dateSent = dateSent; }

}