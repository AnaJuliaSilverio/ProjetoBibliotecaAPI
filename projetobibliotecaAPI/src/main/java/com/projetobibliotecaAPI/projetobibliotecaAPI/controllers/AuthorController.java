package com.projetobibliotecaAPI.projetobibliotecaAPI.controllers;

import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/autor")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody @Valid AuthorRequestDTO authorRequestDTO){
       AuthorResponseDTO authorResponseDTO = authorService.createAuthor(authorRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorResponseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> findyAuthorById(@PathVariable(value = "id") Long id){
        AuthorResponseDTO authorResponseDTO = authorService.getAuthorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(authorResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthor(){
        List<AuthorResponseDTO> authorResponseDTO = authorService.getAllAuthor();
        return ResponseEntity.status(HttpStatus.OK).body(authorResponseDTO);
    }
    @PutMapping
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@RequestBody @Valid AuthorResponseDTO authorResponseDTO){
        AuthorResponseDTO authorResponse = authorService.upadteAuthor(authorResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(authorResponseDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthor(@PathVariable(value = "id") Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
