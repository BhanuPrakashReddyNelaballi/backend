package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.FinePaymentRequestDto;
import com.libraryManagement.entities.Fine;

import java.util.List;

public interface FineService {
    List<Fine> getMemberFines(Long memberId);
    List<Fine> getPendingFines();
    Fine payFine(FinePaymentRequestDto request);
    void calculateOverdueFines();
}