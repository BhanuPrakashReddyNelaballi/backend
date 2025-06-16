package com.libraryManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryManagement.entities.Member;
import com.libraryManagement.entities.Notification;
import com.libraryManagement.repository.MemberRepository;
import com.libraryManagement.repository.NotificationRepository;
import com.libraryManagement.service.DueDateCheckerService;
import com.libraryManagement.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(NotificationController.class)
@SpringBootTest
public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DueDateCheckerService dueDateCheckerService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        Mockito.reset(dueDateCheckerService, notificationRepository, memberRepository, jwtUtil);
    }

    @Test
    void getUserNotifications_ReturnsNotificationList() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(notificationController).build();
        String token = "Bearer sample-token";
        String email = "john@example.com";
        Long memberId = 1L;

        Member member = new Member();
        member.setMemberId(memberId);
        member.setEmail(email);

        Notification notification = new Notification();
        notification.setNotificationID(1);
        notification.setMember(member);
        notification.setMessage("Your book is overdue!");
        notification.setDateSent(LocalDate.now());

        Mockito.when(jwtUtil.extractEmail("sample-token")).thenReturn(email);
        Mockito.when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        Mockito.when(notificationRepository.findByMember_MemberId(memberId)).thenReturn(Collections.singletonList(notification));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notifications")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].message").value("Your book is overdue!"));
    }
}
