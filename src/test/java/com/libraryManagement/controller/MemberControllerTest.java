package com.libraryManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryManagement.dto.requestDto.MemberUpdateDto;
import com.libraryManagement.dto.responseDto.MemberResponseDto;
import com.libraryManagement.enums.MembershipStatus;
import com.libraryManagement.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(MemberController.class)
@SpringBootTest
public class MemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    void setUp() {
        Mockito.reset(memberService);
    }

    @Test
    void getAllMembers_ReturnsMemberList() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(memberController).build();
        MemberResponseDto memberResponse = new MemberResponseDto(1L, "John Doe", "john@example.com", "1234567890", "123 Street", MembershipStatus.ACTIVE, null);

        Mockito.when(memberService.getAllMembers()).thenReturn(Collections.singletonList(memberResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/getMembers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getMemberById_ReturnsMember() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(memberController).build();
        Long memberId = 1L;
        MemberResponseDto memberResponse = new MemberResponseDto(memberId, "John Doe", "john@example.com", "1234567890", "123 Street", MembershipStatus.ACTIVE, null);

        Mockito.when(memberService.getMemberById(memberId)).thenReturn(Optional.of(memberResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void updateMember_ReturnsUpdatedMember() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(memberController).build();
        Long memberId = 1L;
        MemberUpdateDto requestDto = new MemberUpdateDto("John Doe",  "1234567890", "123 Street");
        MemberResponseDto responseDto = new MemberResponseDto(memberId, "John Doe", "john@example.com", "1234567890", "123 Street", MembershipStatus.ACTIVE, null);

        Mockito.when(memberService.updateMember(Mockito.eq(memberId), Mockito.any(MemberUpdateDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/members/update/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void deleteMember_ReturnsNoContent() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(memberController).build();
        Long memberId = 1L;

        Mockito.doNothing().when(memberService).deleteMember(memberId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/members/delete/{id}", memberId))
                .andExpect(status().isOk());
    }

    @Test
    void updateMembershipStatus_ReturnsUpdatedMemberStatus() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(memberController).build();
        Long memberId = 1L;
        MembershipStatus newStatus = MembershipStatus.INACTIVE;
        MemberResponseDto responseDto = new MemberResponseDto(memberId, "John Doe", "john@example.com", "1234567890", "123 Street", newStatus, null);

        Mockito.when(memberService.updateMembershipStatus(memberId, newStatus)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/members/update/membershipStatus/{id}", memberId)
                        .param("status", newStatus.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.membershipStatus").value("INACTIVE"));
    }
}
