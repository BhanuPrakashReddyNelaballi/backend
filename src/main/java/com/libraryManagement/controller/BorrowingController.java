package com.libraryManagement.controller;

import com.libraryManagement.dto.requestDto.BorrowRequestDto;
import com.libraryManagement.dto.responseDto.BorrowingDto;
import com.libraryManagement.dto.responseDto.BorrowingResponseDto;
import com.libraryManagement.dto.requestDto.ReturnRequestDto;
import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.entities.Member;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/transactions")
//@CrossOrigin(origins = "*")
public class
BorrowingController {

    @Autowired
    private final BorrowingService borrowingService;

    @Autowired
    private  MemberRepository memberRepository;
    private static final Logger logger = LoggerFactory.getLogger(BorrowingController.class);

    @Autowired
    public BorrowingController(BorrowingService borrowingService){
        this.borrowingService=borrowingService;
    }
    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/borrow")
    public ResponseEntity<BorrowingResponseDto> borrowBook(@RequestBody BorrowRequestDto request) {
        BorrowingResponseDto response=borrowingService.borrowBook(request);
        logger.info("Book Borrowed Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/return")
    public ResponseEntity<BorrowingResponseDto> returnBook(@RequestBody ReturnRequestDto request) {
        BorrowingResponseDto response=borrowingService.returnBook(request);
        logger.info("Book Returned Successfully");
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/my-transactions")
    public ResponseEntity<List<BorrowingTransaction>> getMyTransactions(Authentication authentication) {
        String email = authentication.getName(); // assuming email is username
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

        List<BorrowingTransaction> transactions = borrowingService.getMemberTransactions(member.getMemberId());
        return ResponseEntity.ok(transactions);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowingTransaction>> getMemberTransactions(@PathVariable Long memberId) {
        logger.info("Transaction Details Fetched Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(borrowingService.getMemberTransactions(memberId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowingTransaction>> getOverdueTransactions() {
        logger.info("Overdue Transaction Details Fetched Successfully");

        return ResponseEntity.status(HttpStatus.OK).body(borrowingService.getOverdueTransactions());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @GetMapping("/all")
    public ResponseEntity<List<BorrowingDto>> getAllTransactions(){
        List<BorrowingTransaction> txs= borrowingService.getAllTransactions();
        List<BorrowingDto> dtos=txs.stream().map(tx-> new BorrowingDto(
                tx.getTransactionID(),
                tx.getBook().getBookId(),
                tx.getMember().getMemberId(),
                tx.getBorrowDate(),
                tx.getReturnDate(),
                tx.getStatus()
        )).toList();
        return ResponseEntity.ok(dtos);
    }
}
