package com.projetobibliotecaAPI.projetobibliotecaAPI.controllers;

import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.LoanResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("livro/{idlivro}/emprestimos")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@PathVariable("idlivro") Long bookId,
                                                      @RequestBody LoanRequestDTO loanRequestDTO){
        LoanResponseDTO loanResponseDTO = loanService.createLoan(bookId,loanRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponseDTO);
    }
    @GetMapping("/{idloan}")
    public ResponseEntity<LoanResponseDTO> findyLoanById(@PathVariable("idloan") Long loanId){
        LoanResponseDTO loanResponseDTO = loanService.finLoanById(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(loanResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans(){
        List<LoanResponseDTO> loanResponseDTOS = loanService.getAllLoan();
        return ResponseEntity.status(HttpStatus.OK).body(loanResponseDTOS);
    }
    @PutMapping
    public ResponseEntity<LoanResponseDTO> updateLoan(@PathVariable("idlivro")Long bookId,@RequestBody LoanResponseDTO loanResponseDTO){
        LoanResponseDTO loanResponseDTO1 = loanService.updateLoan(bookId,loanResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loanResponseDTO1);
    }
    @DeleteMapping("/{idloan}")
    public ResponseEntity deleteLoan(@PathVariable("idloan") Long loanId){
        loanService.deleteBook(loanId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
