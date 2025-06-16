package com.libraryManagement.repository;

import com.libraryManagement.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(Long id);
    Optional<Member> findByEmail(String email);
    List<Member> findByNameContainingIgnoreCase(String name);
    List<Member> findByEmailContainingIgnoreCase(String email);
}
