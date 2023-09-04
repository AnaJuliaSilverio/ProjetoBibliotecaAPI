package com.projetobibliotecaAPI.projetobibliotecaAPI.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String genre;
    private Author author;

    @NotBlank
    @ISBN(type = org.hibernate.validator.constraints.ISBN.Type.ANY)
    private String isbn;

    @Past
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate realeseDate;

}
