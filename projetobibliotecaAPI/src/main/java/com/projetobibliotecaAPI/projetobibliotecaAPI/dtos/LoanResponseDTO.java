package com.projetobibliotecaAPI.projetobibliotecaAPI.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Long id;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate loanDate;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;
    private Book book;
    @NotBlank
    private String status;
}
