package com.libraryManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryManagement.dto.requestDto.BorrowRequestDto;
import com.libraryManagement.dto.requestDto.ReturnRequestDto;
import com.libraryManagement.dto.responseDto.BorrowingResponseDto;
import com.libraryManagement.entities.BorrowingTransaction;
import com.libraryManagement.enums.TransactionStatus;
import com.libraryManagement.service.BorrowingService;
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

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(BorrowingController.class)
@SpringBootTest
public class BorrowingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BorrowingService borrowingService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private BorrowingController borrowingController;

    @BeforeEach
    void setUp() {
        Mockito.reset(borrowingService);
    }

    @Test
    void borrowBook_ReturnsBorrowingResponse() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(borrowingController).build();
        BorrowRequestDto requestDto = new BorrowRequestDto(1L, 101);
        BorrowingResponseDto responseDto = new BorrowingResponseDto(1L, "Book borrowed successfully.");

        Mockito.when(borrowingService.borrowBook(Mockito.any(BorrowRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.message").value("Book borrowed successfully."));
    }

    @Test
    void returnBook_ReturnsBorrowingResponse() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(borrowingController).build();
        ReturnRequestDto requestDto = new ReturnRequestDto(1L);
        BorrowingResponseDto responseDto = new BorrowingResponseDto(1L, "Book returned successfully.");

        Mockito.when(borrowingService.returnBook(Mockito.any(ReturnRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.message").value("Book returned successfully."));
    }

    @Test
    void getMemberTransactions_ReturnsTransactionList() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(borrowingController).build();
        Long memberId = 1L;
        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setTransactionID(1L);
        transaction.setStatus(TransactionStatus.BORROWED);
        transaction.setBorrowDate(LocalDate.now());

        Mockito.when(borrowingService.getMemberTransactions(memberId)).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions/member/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getOverdueTransactions_ReturnsOverdueTransactions() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(borrowingController).build();
        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setTransactionID(1L);
        transaction.setStatus(TransactionStatus.BORROWED);
        transaction.setBorrowDate(LocalDate.now().minusDays(30));

        Mockito.when(borrowingService.getOverdueTransactions()).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions/overdue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}
