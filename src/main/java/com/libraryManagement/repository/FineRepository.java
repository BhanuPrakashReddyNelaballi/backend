package com.libraryManagement.repository;

import com.libraryManagement.entities.Fine;
import com.libraryManagement.enums.FineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
    List<Fine> findByMember_MemberId(Long memberId);
    List<Fine> findByStatus(FineStatus status);

    Fine findByMember_MemberIdAndStatus(Long memberId, FineStatus fineStatus);
}