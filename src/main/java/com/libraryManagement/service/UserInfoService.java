package com.libraryManagement.service;

import com.libraryManagement.dto.requestDto.RegisterRequestDto;
import com.libraryManagement.entities.Member;
import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.enums.UserRole;
import com.libraryManagement.exceptions.UserAlreadyExistsException;
import com.libraryManagement.exceptions.UserNotFoundException;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.utils.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(MemberRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberDetail = repository.findByEmail(email); // Email used as username

        return memberDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    @Transactional
    public String registerUser(RegisterRequestDto dto) {

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Email already registered. Please use a different email.");
        }

        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setAddress(dto.getAddress());

        member.setPassword(encoder.encode(dto.getPassword()));
        member.setMembershipStatus(MembershipStatus.ACTIVE);
        member.setUserRole(UserRole.MEMBER);

        repository.save(member);
        return "User registered successfully";
    }

    public Optional<Member> getUserById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public String updateUser(Long userId, Member updatedMember) {
        Member member = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        member.setName(updatedMember.getName());
        member.setPhone(updatedMember.getPhone());
        member.setAddress(updatedMember.getAddress());

        if (!member.getEmail().equals(updatedMember.getEmail())) {
            throw new RuntimeException("Email update is not allowed. Once registered, email cannot be changed.");
        }

        member.setMembershipStatus(updatedMember.getMembershipStatus());

        if (updatedMember.getPassword() != null && !updatedMember.getPassword().isEmpty()) {
            member.setPassword(encoder.encode(updatedMember.getPassword()));
        }

        repository.save(member);
        return "User updated successfully";
    }

}
