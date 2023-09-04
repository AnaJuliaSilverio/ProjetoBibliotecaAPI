package com.projetobibliotecaAPI.projetobibliotecaAPI.services;


import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Author;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Book;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.BookUnavailableException;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Loan;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.BookRepository;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.LoanRepository;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.LoanService;
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

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private BookRepository bookRepository;
    @Spy
    ModelMapper modelMapper;
    @InjectMocks
    private LoanService loanService;
    private Book book;
    private Loan loan;
    private Author author;
    @BeforeEach
    void setUpMock() throws Exception{
        author = new Author(1L,"Ana","ana@exemplo.com","16147684639");
        book = new Book(1L,"123456","Book test","Drama", LocalDate.now(),author);
        loan = new Loan(1L,LocalDate.now(),LocalDate.parse("2024-10-10"),"devolvido",book);
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void whenCreatALoanShouldReturnLoan(){
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO(LocalDate.now(),LocalDate.parse("2024-10-10"));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        var response= loanService.createLoan(1L,loanRequestDTO);
        verify(bookRepository,times(2)).findById(1L);
        verify(loanRepository,times(1)).save(any(Loan.class));
        assertNotNull(response);
        assertEquals("Alugado",response.getStatus());

    }
    @Test
    void whenCreatALoanWithInvalidIdBookShouldThrowAnExpection(){
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO(LocalDate.now(),LocalDate.parse("2024-10-10"));
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> loanService.createLoan(99L,loanRequestDTO));
        verify(bookRepository,times(1)).findById(99L);
        verify(loanRepository,never()).save(any(Loan.class));
    }
    @Test
    void whenCreatALoanWithInvalidDateShouldThrowAnExpection(){
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO(LocalDate.now(),LocalDate.parse("2022-10-10"));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertThrows(DateTimeException.class, () -> loanService.createLoan(1L,loanRequestDTO));
        verify(bookRepository,times(2)).findById(1L);
        verify(loanRepository,never()).save(any(Loan.class));
    }
    @Test
    void shouldThrowBookUnvailbleAnExpection(){
        loan.setStatus("alugado");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.findByBook(book)).thenReturn(Collections.singletonList(loan));
        assertThrows(BookUnavailableException.class, () -> loanService.verifyLoanStatus(1L));
    }
    @Test
    void shouldReturnListOfLoan(){
        when(loanRepository.findAll()).thenReturn(Collections.singletonList(loan));
        List<LoanResponseDTO> loanList = loanService.getAllLoan();
        assertNotNull(loanList);
        assertEquals(loan.getStatus(),loanList.get(0).getStatus());
    }
    @Test
    void whenSerchAnIdShouldReturnLoan(){
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        var loanResponseDTO = loanService.finLoanById(1L);
        verify(loanRepository, times(1)).findById(1L);
        assertNotNull(loanResponseDTO);
        assertEquals(loan.getStatus(),loanResponseDTO.getStatus());
    }

    @Test
    void whenSerchAnInvalidIdShouldThrowException(){
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> loanService.finLoanById(99L));
        verify(loanRepository, times(1)).findById(99L);
    }
    @Test
    void whenDeleteAnLoanShouldReturnNothing(){
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        loanService.deleteLoan(1L);
        verify(loanRepository, times(1)).findById(1L);
        verify(loanRepository,times(1)).delete(loan);
    }
    @Test
    void whenDeleteALoanWithInvalidIDShouldThrowException(){
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> loanService.finLoanById(99L));
        verify(loanRepository, times(1)).findById(99L);
        verify(loanRepository,never()).delete(loan);
    }
    @Test
    void ShoulReturnAnUptadeLoan(){
        LoanResponseDTO loanResponseDTO= new LoanResponseDTO(1L,LocalDate.now(),LocalDate.parse("2024-10-10"),book,"devolvido");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.findById(loanResponseDTO.getId())).thenReturn(Optional.of(loan));

        var response = loanService.updateLoan(1L,loanResponseDTO);

        verify(bookRepository, times(1)).findById(1L);
        assertEquals(loanResponseDTO.getStatus(),response.getStatus());

    }
    @Test
    void  whenUpdateLoanWithInvalidIDShouldThrowException(){
        LoanResponseDTO loanResponseDTO= new LoanResponseDTO(99L,LocalDate.now(),LocalDate.parse("2024-10-10"),book,"devolvido");;

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.findById(loanResponseDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> loanService.updateLoan(1L,loanResponseDTO));
        verify(loanRepository, times(1)).findById(99L);
            verify(loanRepository,never()).save(loan);
    }
    @Test
    void  whenUpdateLoanWithInvalidBookShouldThrowException(){
        LoanResponseDTO loanResponseDTO= new LoanResponseDTO(1L,LocalDate.now(),LocalDate.parse("2024-10-10"),book,"devolvido");;

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> loanService.updateLoan(99L,loanResponseDTO));
        verify(bookRepository,times(1)).findById(99L);
        verify(loanRepository, never()).findById(1L);
        verify(loanRepository,never()).save(loan);
    }
}
