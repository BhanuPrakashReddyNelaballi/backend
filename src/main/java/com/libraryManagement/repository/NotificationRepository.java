package com.libraryManagement.repository;

import com.libraryManagement.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByMember_MemberId(Long memberId);

    boolean existsByMember_MemberIdAndMessageContaining(Long memberId, String s);
}
