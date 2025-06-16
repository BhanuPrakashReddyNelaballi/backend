package com.libraryManagement.repository;

import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    long countByMember_MemberIdAndStatus(Long memberId, TransactionStatus status);
    List<BorrowingTransaction> findByMember_MemberId(Long memberId);
    List<BorrowingTransaction> findByStatusAndBorrowDateBefore(TransactionStatus status, LocalDate borrowDate);
    List<BorrowingTransaction> findByStatusAndReturnDateIsNullAndBorrowDateBefore(TransactionStatus status, LocalDate borrowDate);

}