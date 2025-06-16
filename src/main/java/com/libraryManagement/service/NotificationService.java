package com.libraryManagement.service;

import com.libraryManagement.entities.Member;
import com.libraryManagement.entities.Notification;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberRepository memberRepository;

    public void createNotification(Long memberId, int bookId, String bookTitle, String message) {
        logger.info("Checking if Notification already exists for Member ID=" + memberId + ", Book ID=" + bookId);

        boolean notificationExists =
                notificationRepository.existsByMember_MemberIdAndMessageContaining(memberId, "Book ID: " + bookId)
                && notificationRepository.existsByMember_MemberIdAndMessageContaining(memberId,"overdue");

        if (notificationExists) {
            logger.info("Notification already exists, skipping creation.");
            return; // Skip creating a new notification
        }

        logger.info("Creating Notification for Member ID=" + memberId + ", Book ID=" + bookId);
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        logger.info("Member Found: " + member.getName());

        Notification notification = new Notification();
        notification.setMember(member);
        notification.setMessage("Book ID: " + bookId + " (" + bookTitle + ") - " + message);
        notification.setDateSent(LocalDate.now());

        notificationRepository.save(notification);
        logger.info("Notification Saved in Database");
    }


    public void removeNotificationsForReturnedBook(Long memberId, int bookId) {
        logger.info("Deleting Notifications for Returned Book ID=" + bookId);}}

