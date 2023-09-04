package com.projetobibliotecaAPI.projetobibliotecaAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.AuthorService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;
    @Autowired
    private AuthorController authorController;

    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .alwaysDo(print())
                .build();
    }

    @Test
    void shouldReturnSucessMethodPost() throws Exception {
        AuthorRequestDTO authorRequestDTO = new AuthorRequestDTO("Ana", "ana@exemplo.com", "16147684639");
        when(authorService.createAuthor(authorRequestDTO)).thenReturn(new AuthorResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/autor")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(authorRequestDTO)))
        .andExpect(status().isCreated());;
    }
    @Test
    void shouldReturnSucessMethodGetById() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(new AuthorResponseDTO());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/autor/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());;
    }
    @Test
    void shouldReturnSucessMethodGetByAll() throws Exception {
        when(authorService.getAllAuthor()).thenReturn(Collections.singletonList(new AuthorResponseDTO()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/autor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());;
    }



}
