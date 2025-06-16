package com.libraryManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryManagement.dto.requestDto.FinePaymentRequestDto;
import com.libraryManagement.entities.Fine;
import com.libraryManagement.enums.FineStatus;
import com.libraryManagement.service.FineService;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(FineController.class)
@SpringBootTest
public class FineControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FineService fineService;

    @InjectMocks
    private FineController fineController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMemberFines_ReturnsFineList() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(fineController).build();
        Long memberId = 1L;
        Fine fine = new Fine();
        fine.setFineID(1);
        fine.setAmount(100);
        fine.setStatus(FineStatus.PENDING);
        fine.setTransactionDate(LocalDate.now());

        Mockito.when(fineService.getMemberFines(memberId)).thenReturn(List.of(fine));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/fines/member/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void payFine_ReturnsUpdatedFine() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(fineController).build();
        FinePaymentRequestDto requestDto = new FinePaymentRequestDto(1);
        Fine fine = new Fine();
        fine.setFineID(1);
        fine.setAmount(100);
        fine.setStatus(FineStatus.PAID);
        fine.setTransactionDate(LocalDate.now());

        Mockito.when(fineService.payFine(Mockito.any(FinePaymentRequestDto.class))).thenReturn(fine);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/fines/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    void getPendingFines_ReturnsPendingFines() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(fineController).build();
        Fine fine = new Fine();
        fine.setFineID(1);
        fine.setAmount(100);
        fine.setStatus(FineStatus.PENDING);
        fine.setTransactionDate(LocalDate.now());

        Mockito.when(fineService.getPendingFines()).thenReturn(Collections.singletonList(fine));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/fines/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }


    @Test
    void calculateOverdueFines_ReturnsOk() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(fineController).build();
        Mockito.doNothing().when(fineService).calculateOverdueFines();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/fines/calculate"))
                .andExpect(status().isOk());
    }
}
