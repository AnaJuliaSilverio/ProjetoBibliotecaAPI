package com.projetobibliotecaAPI.projetobibliotecaAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
public class LoanControllerTest {
    @MockBean
    private LoanService loanService;
    @Autowired
    private LoanController loanController;

    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(loanController)
                .alwaysDo(print())
                .build();
    }
    @Test
    void shouldReturnSucessMethodPost() throws Exception {
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO(LocalDate.now(),LocalDate.parse("2024-10-10"));
        when(loanService.createLoan(1L,loanRequestDTO)).thenReturn(new LoanResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/livro/{idlivro}/emprestimos",1L)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loanRequestDTO)))
                .andExpect(status().isCreated());;
    }
    @Test
    void shouldReturnSucessMethodGetById() throws Exception {
        when(loanService.finLoanById(1L)).thenReturn(new LoanResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/livro/{idlivro}/emprestimos/{idemprestimo}",1L,1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());;
    }
    @Test
    void shouldReturnSucessMethodGet() throws Exception {
        when(loanService.getAllLoan()).thenReturn(Collections.singletonList(new LoanResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/livro/{idlivro}/emprestimos",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());;
    }
}
