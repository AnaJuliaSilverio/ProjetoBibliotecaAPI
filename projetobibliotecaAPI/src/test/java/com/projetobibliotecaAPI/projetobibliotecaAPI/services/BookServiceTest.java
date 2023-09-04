package com.projetobibliotecaAPI.projetobibliotecaAPI.services;


import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Author;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Book;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.AuthorRepository;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.BookRepository;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.BookService;
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

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @Spy
    ModelMapper modelMapper;
    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;
    @BeforeEach
    void setUpMock() throws Exception{
        author = new Author(1L,"Ana","ana@exemplo.com","16147684639");
        book = new Book(1L,"123456","Book test","Drama", LocalDate.now(),author);
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void whenCreatedBookrShouldReturnBook(){
        BookRequestDTO bookRequestDTO = new BookRequestDTO("Book test","Drama","123456",LocalDate.now());

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(1L);
            return savedBook;
        });

        var response = bookService.createBook(1L,bookRequestDTO);
        assertNotNull(response);
        assertEquals("Book test",response.getTitle());
        assertEquals(1L,response.getId());
    }

    @Test
    void shouldThrowAnExceptionWhenSearchAnIvalidAuthorId(){
        BookRequestDTO bookRequestDTO = new BookRequestDTO("Book test","Drama","123456",LocalDate.now());
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookService.createBook(99L,bookRequestDTO));
        verify(authorRepository, times(1)).findById(99L);
        verify(bookRepository,never()).save(any(Book.class));
    }
    @Test
    void shouldReturnListOfBooks(){
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        List<BookResponseDTO> bookList = bookService.getAllBooks();
        assertNotNull(bookList);
        assertEquals(book.getTitle(),bookList.get(0).getTitle());
    }
    @Test
    void whenSerchAnIdShouldReturnBook(){
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        BookResponseDTO bookResponseDTO = bookService.finBookById(1L);
        verify(bookRepository, times(1)).findById(1L);
        assertNotNull(bookResponseDTO);
        assertEquals(book.getTitle(),bookResponseDTO.getTitle());
    }
    @Test
    void whenSerchAnInvalidIdShouldThrowException(){
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookService.finBookById(99L));
        verify(bookRepository, times(1)).findById(99L);
    }
    @Test
    void whenDeleteABookShouldReturnNothing(){
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).findById(1L);
    }
    @Test
    void whenDeleteABookWithInvalidIDShouldThrowException(){
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.finBookById(99L));
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository,never()).delete(book);
    }
    @Test
    void ShoulReturnAnUptadeBook(){
        BookResponseDTO bookResponseDTO = new BookResponseDTO(1L,"Book test","Drama",author,"123456", LocalDate.now());

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.findById(bookResponseDTO.getId())).thenReturn(Optional.of(book));

        BookResponseDTO responseDTO = bookService.updateBook(1L,bookResponseDTO);

        verify(bookRepository, times(1)).findById(1L);
        assertEquals(responseDTO.getTitle(),bookResponseDTO.getTitle());
        assertEquals(responseDTO.getId(),bookResponseDTO.getId());
    }
    @Test
    void  whenUpdateABookWithInvalidIDShouldThrowException(){
        BookResponseDTO bookResponseDTO = new BookResponseDTO(99L,"Book test","Drama",author,"123456", LocalDate.now());

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.findById(bookResponseDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> bookService.updateBook(1L,bookResponseDTO));
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository,never()).save(book);
    }

    @Test
    void  whenUpdateABookWithInvalidIdOfAuthorShouldThrowException(){
        BookResponseDTO bookResponseDTO = new BookResponseDTO(1L,"Book test","Drama",author,"123456", LocalDate.now());

        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> bookService.updateBook(99L,bookResponseDTO));

        verify(authorRepository, times(1)).findById(99L);
        verify(bookRepository,never()).findById(1L);
        verify(bookRepository,never()).save(book);
    }
}
