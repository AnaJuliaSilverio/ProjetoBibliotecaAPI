package com.projetobibliotecaAPI.projetobibliotecaAPI.controllers;

import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autor/{idautor}/livros")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> registerBook(@PathVariable("idautor") Long idAutor,
                                       @RequestBody BookRequestDTO bookRequestDTO){
        BookResponseDTO bookResponseDTO = bookService.createBook(idAutor,bookRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDTO);
    }
    @GetMapping("/{idbook}")
    public ResponseEntity<BookResponseDTO> findBookById(@PathVariable("idbook") Long idBook){
        BookResponseDTO bookResponseDTO = bookService.finBookById(idBook);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(){
        List<BookResponseDTO> bookResponseDTOS = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDTOS);
    }
    @PutMapping
    private ResponseEntity<BookResponseDTO> updateBook(@PathVariable("idautor") Long idAutor,@RequestBody BookResponseDTO bookResponseDTO){
        BookResponseDTO bookResponseDTO1 = bookService.updateBook(idAutor,bookResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDTO);
    }
    @DeleteMapping("/{idbook}")
    public ResponseEntity deleteBook(@PathVariable("idbook")Long idBook){
        bookService.deleteBook(idBook);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
