package com.libraryManagement.service;

import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.repository.BorrowingTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DueDateCheckerService {
    private static final Logger logger = LoggerFactory.getLogger(DueDateCheckerService.class);

    @Autowired
    private BorrowingTransactionRepository borrowingTransactionRepository;

    @Autowired
    private NotificationService notificationService;
    public void checkDueDates() {
        logger.info("Scheduled Task Triggered: Checking Due Dates");

        List<BorrowingTransaction> borrowedBooks = borrowingTransactionRepository.findAll();
        logger.info("Borrowed Books Retrieved: " + borrowedBooks.size());

        for (BorrowingTransaction book : borrowedBooks) {
            logger.info("Processing Borrowed Book: ID=" + book.getBook().getBookId() + ", Borrow Date=" + book.getBorrowDate());

            long daysUntilDue = ChronoUnit.DAYS.between(book.getBorrowDate(), LocalDate.now());
            logger.info("Calculated daysUntilDue=" + daysUntilDue);

            if (daysUntilDue >= 12 && daysUntilDue <= 14) {
                logger.info("Sending Notification for Due in 2 Days");
                notificationService.createNotification(
                        book.getMember().getMemberId(),
                        book.getBook().getBookId(),
                        book.getBook().getTitle(),
                        "Your book is due in 2 days."
                );
            }

            if (daysUntilDue > 14) {
                logger.info("Sending Overdue Notification");
                notificationService.createNotification(
                        book.getMember().getMemberId(),
                        book.getBook().getBookId(),
                        book.getBook().getTitle(),
                        "Your book is overdue. Please return it or pay the overdue fine to keep using the book."
                );
            }
        }

        logger.info("Scheduled Task Completed: Notifications Processed");
    }

}
