// File: src/main/java/com/libraryManagement/controller/AdminController.java
package com.libraryManagement.controller;

import com.libraryManagement.dto.responseDto.DashboardStatsDto;
import com.libraryManagement.service.BookService;
import com.libraryManagement.service.MemberService;
import com.libraryManagement.service.BorrowingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowingService borrowingService;

    @Autowired
    public AdminController(BookService bookService,
                           MemberService memberService,
                           BorrowingService borrowingService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowingService = borrowingService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        long totalBooks     = bookService.countAllBooks();
        long totalMembers   = memberService.countAllMembers();

        DashboardStatsDto dto = new DashboardStatsDto(totalBooks, totalMembers);
        return ResponseEntity.ok(dto);
    }
}
 