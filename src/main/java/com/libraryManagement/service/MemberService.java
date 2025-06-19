package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.MemberRequestDto;
import com.libraryManagement.dto.requestDto.MemberUpdateDto;
import com.libraryManagement.dto.responseDto.MemberResponseDto;
import com.libraryManagement.enums.MembershipStatus;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<MemberResponseDto> getAllMembers();
    Optional<MemberResponseDto> getMemberById(Long id);
    MemberResponseDto updateMember(Long id, MemberUpdateDto updatedMemberRequestDto);
    void deleteMember(Long id);
    List<MemberResponseDto> searchByName(String name);
    List<MemberResponseDto> searchByEmail(String email);
    MemberResponseDto updateMembershipStatus(Long id, MembershipStatus status);
    long countAllMembers();
}
