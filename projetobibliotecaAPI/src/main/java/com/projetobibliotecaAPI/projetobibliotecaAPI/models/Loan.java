package com.projetobibliotecaAPI.projetobibliotecaAPI.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loan")
    private Long id;

    @Column(name = "loan_date",nullable = false)
    private LocalDate loanDate;
    @Column(name = "return_date",nullable = false)
    private LocalDate returnDate;
    @Column(nullable = false)
    private String status;
    @ManyToOne
    @JoinColumn(name = "fk_id_book")
    private Book book;
}
