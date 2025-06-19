package com.libraryManagement.controller;

import com.libraryManagement.dto.requestDto.MemberRequestDto;
import com.libraryManagement.dto.requestDto.MemberUpdateDto;
import com.libraryManagement.dto.responseDto.MemberResponseDto;
import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @GetMapping("/getMembers")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public List<MemberResponseDto> getAllMembers() {
        logger.info("All Members Details Fetched Successfully");

        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public MemberResponseDto getMemberById(@PathVariable Long id) {
        logger.info("Member details with a specific ID Fetched Successfully");

        return memberService.getMemberById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public MemberResponseDto updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto updatedMemberRequestDto) {
        logger.info("Member Details Updated Successfully");

        return memberService.updateMember(id, updatedMemberRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public void deleteMember(@PathVariable Long id) {
        logger.info("Member Details deleted Successfully");

        memberService.deleteMember(id);
    }

    @GetMapping("/search/name/{name}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public List<MemberResponseDto> searchByName(@PathVariable String name) {

        return memberService.searchByName(name);
    }

    @GetMapping("/search/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public List<MemberResponseDto> searchByEmail(@PathVariable String email) {
        logger.info("Member Details by email Fetched Successfully");

        return memberService.searchByEmail(email);
    }

    @PutMapping("/update/membershipStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MemberResponseDto updateMembershipStatus(@PathVariable Long id, @RequestParam MembershipStatus status) {
        logger.info("Membership Details updated Successfully");

        return memberService.updateMembershipStatus(id, status);
    }
}
