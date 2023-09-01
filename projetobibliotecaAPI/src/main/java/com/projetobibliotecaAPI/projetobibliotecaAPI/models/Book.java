package com.projetobibliotecaAPI.projetobibliotecaAPI.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private String ISBN;

    @Column(name = "realese_date",nullable = false)
    private LocalDate realeseDate;

    @ManyToOne
    private Author author;

}
