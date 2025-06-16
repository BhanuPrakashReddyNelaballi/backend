package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.FinePaymentRequestDto;
import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.entities.Fine;
import com.libraryManagement.enums.FineStatus;
import com.libraryManagement.enums.TransactionStatus;
import com.libraryManagement.exceptions.FinePaymentException;
import com.libraryManagement.repository.BorrowingTransactionRepository;
import com.libraryManagement.repository.FineRepository;
import com.libraryManagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FineServiceImpl implements FineService {

    private static final double FINE_PER_DAY = 0.50;

    @Autowired
    private FineRepository fineRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private BorrowingTransactionRepository transactionRepo;

    @Override
    public List<Fine> getMemberFines(Long memberId) {
        return fineRepo.findByMember_MemberId(memberId);
    }

    @Override
    public List<Fine> getPendingFines() {
        return fineRepo.findByStatus(FineStatus.PENDING);
    }

    @Override
    public Fine payFine(FinePaymentRequestDto request) {
        Fine fine = fineRepo.findById(request.getFineId())
                .orElseThrow(() -> new FinePaymentException("Invalid fine ID"));

        if (fine.getStatus() == FineStatus.PAID) {
            throw new FinePaymentException("Fine is already paid");
        }

        fine.setStatus(FineStatus.PAID);
        fine.setLastPaymentDate(LocalDate.now());
        return fineRepo.save(fine);
    }
    @Override
    public void calculateOverdueFines() {
        List<BorrowingTransaction> overdueTransactions = transactionRepo
                .findByStatusAndReturnDateIsNullAndBorrowDateBefore(
                        TransactionStatus.BORROWED,
                        LocalDate.now().minusDays(14)
                );

        for (BorrowingTransaction tx : overdueTransactions) {
            Fine existingFine = fineRepo.findByMember_MemberIdAndStatus(tx.getMember().getMemberId(), FineStatus.PAID);

            LocalDate fineStartDate = (existingFine != null && existingFine.getLastPaymentDate() != null)
                    ? existingFine.getLastPaymentDate()
                    : tx.getBorrowDate().plusDays(14);

            long overdueDays = ChronoUnit.DAYS.between(fineStartDate, LocalDate.now());

            if (overdueDays > 0) {
                double fineAmount = overdueDays * FINE_PER_DAY;

                Fine newFine = new Fine();
                newFine.setMember(tx.getMember());
                newFine.setAmount(fineAmount);
                newFine.setStatus(FineStatus.PENDING);
                newFine.setTransactionDate(LocalDate.now());
                fineRepo.save(newFine);
            }
        }
    }

//
//    @Override
//    public void calculateOverdueFines() {
//        List<BorrowingTransaction> overdueTransactions = transactionRepo
//                .findByStatusAndReturnDateIsNullAndBorrowDateBefore(
//                        TransactionStatus.BORROWED,
//                        LocalDate.now().minusDays(14)
//                );
//
//        for (BorrowingTransaction tx : overdueTransactions) {
//            long overdueDays = ChronoUnit.DAYS.between(
//                    tx.getBorrowDate().plusDays(14),
//                    LocalDate.now()
//            );
//
//            if (overdueDays > 0) {
//                double fineAmount = overdueDays * FINE_PER_DAY;
//
//                Fine fine = new Fine();
//                fine.setMember(tx.getMember());
//                fine.setAmount(fineAmount);
//                fine.setStatus(FineStatus.PENDING);
//                fine.setTransactionDate(LocalDate.now());
//                fineRepo.save(fine);
//            }
//        }
//    }
}