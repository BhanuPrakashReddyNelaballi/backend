package com.libraryManagement.controller;

import com.libraryManagement.dto.requestDto.FinePaymentRequestDto;
import com.libraryManagement.entities.Fine;
import com.libraryManagement.entities.Member;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.service.FineService;
import com.libraryManagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(FineController.class);


    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('MEMBER')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Fine>> getMemberFines(
            @PathVariable Long memberId,
            Authentication authentication) {

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEMBER"))) {
            String email = authentication.getName();
            Member me = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            if (!me.getMemberId().equals(memberId)) {
                // Forbidden if they try to fetch another member’s fines
                return ResponseEntity.status(403).build();
            }
        }

        logger.info("Member Fine Details Fetched Successfully for memberId={}", memberId);
        List<Fine> fines = fineService.getMemberFines(memberId);
        return ResponseEntity.ok(fines);
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
