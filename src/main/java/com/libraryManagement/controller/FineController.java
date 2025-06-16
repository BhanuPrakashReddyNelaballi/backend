package com.libraryManagement.controller;

import com.libraryManagement.dto.requestDto.FinePaymentRequestDto;
import com.libraryManagement.entities.Fine;
import com.libraryManagement.service.FineService;
import com.libraryManagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/fines")
@CrossOrigin(origins = "*")
public class FineController {

    @Autowired
    private FineService fineService;
    @Autowired
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(FineController.class);


    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Fine>> getMemberFines(@PathVariable Long memberId) {
        logger.info("Member Fine Details Fetched Successfully");

        return ResponseEntity.ok(fineService.getMemberFines(memberId));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @GetMapping("/pending")
    public ResponseEntity<List<Fine>> getPendingFines() {
        logger.info("Pending Fine Details Fetched Successfully");

        return ResponseEntity.ok(fineService.getPendingFines());
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/pay")
    public ResponseEntity<Fine> payFine(@RequestBody FinePaymentRequestDto request) {
        Fine paidFine = fineService.payFine(request);
        logger.info("Fine Paid Successfully");

        return ResponseEntity.ok(paidFine);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    @PostMapping("/calculate")
    public ResponseEntity<Void> calculateOverdueFines() {
        fineService.calculateOverdueFines();
        logger.info("Fine Calculated Successfully");

        return ResponseEntity.ok().build();
    }
}
