package com.projetobibliotecaAPI.projetobibliotecaAPI.services;


import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Author;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.AuthorRepository;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    private AuthorService authorService;
    private Author author;


    @BeforeEach
    void setUpMock() throws Exception{
        author = new Author(1L,"Ana","ana@exemplo.com","16147684639");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreatedAnAuthorShouldReturnAuthor(){
        AuthorRequestDTO authorRequestDTO = new AuthorRequestDTO("Ana", "ana@exemplo.com", "16147684639");
        AuthorResponseDTO authorResponseDTO = authorService.createAuthor(authorRequestDTO);
        assertNotNull(authorResponseDTO);
        assertEquals("Ana", authorResponseDTO.getName());
    }

    @Test
    void Bo(){
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        AuthorResponseDTO responseDTO = authorService.getAuthorById(1L);
        verify(authorRepository, times(1)).findById(1L);
        assertNotNull(responseDTO);
        assertEquals("Ana",responseDTO.getName());
    }
    @Test
    void whenSerchInvalidIdShouldThrowAnException(){
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> authorService.getAuthorById(99L));
        verify(authorRepository, times(1)).findById(99L);
        verify(modelMapper, never()).map(any(), eq(AuthorResponseDTO.class));
    }
    @Test
    void ShouldReturnListOfAuthor(){
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));
        List<AuthorResponseDTO> authorList = authorService.getAllAuthor();
        assertNotNull(authorList);
        assertEquals(author.getName(),authorList.get(0).getName());
    }
    @Test
    void ShoulReturnAnUptadeAuthor(){
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO(1L,"Flavia","flavia@exemplo.com","16147684639");

        when(authorRepository.findById(authorResponseDTO.getId())).thenReturn(Optional.of(author));

        AuthorResponseDTO responseDTO = authorService.upadteAuthor(authorResponseDTO);

        verify(authorRepository, times(1)).findById(authorResponseDTO.getId());
        assertNotNull(responseDTO);
        assertEquals(authorResponseDTO.getName(),responseDTO.getName());
        assertEquals(authorResponseDTO.getEmail(),responseDTO.getEmail());
    }

    @Test
    void whenSerchInvalidAuthorShouldThrowAnExceptior(){
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO(99L,"Flavia","flavia@exemplo.com","16147684639");
        when(authorRepository.findById(authorResponseDTO.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> authorService.upadteAuthor(authorResponseDTO));
        verify(authorRepository, times(1)).findById(authorResponseDTO.getId());
        verify(modelMapper, never()).map(any(), eq(AuthorResponseDTO.class));
    }

    @Test
    void whenDeleteAnAuthorShouldReturnNothing(){
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        authorService.deleteAuthor(1L);
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    public void testDeleteAuthorIfNotExists() {

        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authorService.deleteAuthor(99L));

        verify(authorRepository, times(1)).findById(99L);
        verify(authorRepository, never()).delete(any());
    }



}
