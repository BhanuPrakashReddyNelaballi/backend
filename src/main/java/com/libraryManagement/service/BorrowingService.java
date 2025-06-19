package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.BorrowRequestDto;
import com.libraryManagement.dto.requestDto.ReturnRequestDto;
import com.libraryManagement.dto.responseDto.BorrowingResponseDto;
import com.libraryManagement.entities.BorrowingTransaction;

import java.util.List;

public interface BorrowingService {
    BorrowingResponseDto borrowBook(BorrowRequestDto request);
    BorrowingResponseDto returnBook(ReturnRequestDto request);
    List<BorrowingTransaction> getMemberTransactions(Long memberId);
    List<BorrowingTransaction> getOverdueTransactions();
    List<BorrowingTransaction> getAllTransactions();
}