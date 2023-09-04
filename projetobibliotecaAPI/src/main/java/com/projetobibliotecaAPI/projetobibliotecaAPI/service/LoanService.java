package com.projetobibliotecaAPI.projetobibliotecaAPI.service;


import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Book;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.BookUnavailableException;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Loan;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.BookRepository;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ModelMapper modelMapper;

    public LoanResponseDTO createLoan(Long bookId, LoanRequestDTO loanRequestDTO){
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("Nenhum livro identificado"));
        verifyLoanStatus(bookId);
        if (loanRequestDTO.getLoanDate().isAfter(loanRequestDTO.getReturnDate())) throw new DateTimeException("Data Inválida");
        Loan loan = new Loan();
        loan.setStatus("Alugado");
        modelMapper.map(loanRequestDTO,loan);
        loan.setBook(book);
        loanRepository.save(loan);
        return modelMapper.map(loan,LoanResponseDTO.class);
    }
    public List<LoanResponseDTO> getAllLoan(){
        return loanRepository.findAll().stream()
                .map(loan -> modelMapper.map(loan,LoanResponseDTO.class)).toList();
    }
    public LoanResponseDTO finLoanById(Long id){
        Loan loan = loanRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Nenhum empréstimo identificado"));
        return modelMapper.map(loan,LoanResponseDTO.class);
    }
    public LoanResponseDTO updateLoan(Long bookId,LoanResponseDTO loanResponseDTO){
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("Nenhum livro identificado"));
        Loan loan = loanRepository.findById(loanResponseDTO.getId()).orElseThrow(()-> new EntityNotFoundException("Nenhum empréstimo identificado"));
        if (loanResponseDTO.getLoanDate().isAfter(loanResponseDTO.getReturnDate())) throw new DateTimeException("Data Inválida");
        loan.setLoanDate(loanResponseDTO.getLoanDate());
        loan.setReturnDate(loanResponseDTO.getReturnDate());
        loan.setBook(book);
        loanRepository.save(loan);
        return modelMapper.map(loan,LoanResponseDTO.class);
    }
    public void deleteLoan(Long id){
        Loan loan = loanRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Nenhum empréstimo identificado"));
        loanRepository.delete(loan);
    }

    public void verifyLoanStatus(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("Nenhum livro identificado"));
        List<Loan> loans = loanRepository.findByBook(book);
        loans.forEach(loan -> {
            if (loan.getStatus().equals("alugado")) throw new BookUnavailableException();
        });
    }
}
