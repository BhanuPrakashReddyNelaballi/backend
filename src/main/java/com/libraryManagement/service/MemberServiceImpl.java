package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.MemberRequestDto;
import com.libraryManagement.dto.responseDto.MemberResponseDto;
import com.libraryManagement.entities.Member;
import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.exceptions.EmailUpdateNotAllowedException;
import com.libraryManagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Optional<MemberResponseDto> getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public MemberResponseDto updateMember(Long id, MemberRequestDto updatedMemberRequestDto) {
        return memberRepository.findById(id)
                .map(existingMember -> {
                    existingMember.setName(updatedMemberRequestDto.getName());
                    existingMember.setPhone(updatedMemberRequestDto.getPhone());
                    existingMember.setAddress(updatedMemberRequestDto.getAddress());

                    if (!existingMember.getEmail().equals(updatedMemberRequestDto.getEmail())) {
                        throw new EmailUpdateNotAllowedException("Email update is not allowed. Once registered, email cannot be changed.");
                    }

                    Member updatedMember = memberRepository.save(existingMember);
                    return mapToResponse(updatedMember);
                })
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found with ID: " + id);
        }
        memberRepository.deleteById(id);
    }

    @Override
    public List<MemberResponseDto> searchByName(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<MemberResponseDto> searchByEmail(String email) {
        return memberRepository.findByEmailContainingIgnoreCase(email).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public MemberResponseDto updateMembershipStatus(Long id, MembershipStatus status) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));


        member.setMembershipStatus(status);

        Member updatedMember = memberRepository.save(member);
        return mapToResponse(updatedMember);
    }

    @Override
    public long countAllMembers(){
        return memberRepository.count();
    }
    

    private MemberResponseDto mapToResponse(Member member) {
        return new MemberResponseDto(
                member.getMemberId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getMembershipStatus(),
                member.getUserRole()
        );
    }
}
