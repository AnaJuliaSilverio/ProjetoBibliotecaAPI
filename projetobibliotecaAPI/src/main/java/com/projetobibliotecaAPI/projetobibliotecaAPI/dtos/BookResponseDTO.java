package com.projetobibliotecaAPI.projetobibliotecaAPI.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class BookResponseDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String genero;

    @NotBlank
    @ISBN(type = org.hibernate.validator.constraints.ISBN.Type.ANY)
    private String ISBN;

    @Past
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate realeseDate;

}
