package com.projetobibliotecaAPI.projetobibliotecaAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.BookService;
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

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @MockBean
    private BookService bookService;
    @Autowired
    private BookController bookController;
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .alwaysDo(print())
                .build();
    }
    @Test
    void shouldReturnSucessMethodPost() throws Exception {
        BookRequestDTO bookRequestDTO = new BookRequestDTO("Book test","Drama","123456", LocalDate.now());
        when(bookService.createBook(1L,bookRequestDTO)).thenReturn(new BookResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/autor/{idautor}/livros",1L)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(bookRequestDTO)))
                .andExpect(status().isCreated());;
    }
    @Test
    void shouldReturnSucessMethodGetById() throws Exception {
        when(bookService.finBookById(1L)).thenReturn(new BookResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/autor/{idautor}/livros/{idbook}",1L,1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());;
    }
    @Test
    void shouldReturnSucessMethodGet() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(new BookResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/autor/{idautor}/livros",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());;
    }
}
