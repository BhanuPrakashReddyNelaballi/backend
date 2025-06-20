package com.libraryManagement.controller;

import com.libraryManagement.entities.Member;
import com.libraryManagement.entities.Notification;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.repository.NotificationRepository;
import com.libraryManagement.service.DueDateCheckerService;
import com.libraryManagement.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);


    @Autowired
    private DueDateCheckerService dueDateCheckerService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<List<Notification>> getUserNotifications(@RequestHeader("Authorization") String token) {
        dueDateCheckerService.checkDueDates();
        String jwtToken = token.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(jwtToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("Notifications created Successfully");

        return ResponseEntity.ok(notificationRepository.findByMember_MemberId(member.getMemberId()));
    }

}
