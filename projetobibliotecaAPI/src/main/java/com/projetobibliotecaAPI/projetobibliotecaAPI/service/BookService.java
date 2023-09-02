package com.projetobibliotecaAPI.projetobibliotecaAPI.service;

import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.BookResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Author;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Book;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.AuthorRepository;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ModelMapper modelMapper;

    public BookResponseDTO createBook(Long authorId,BookRequestDTO bookDTO){
        Author author= authorRepository.findById(authorId).orElseThrow(()-> new EntityNotFoundException("Nenhum autor identificado"));
        Book book = new Book();
        modelMapper.map(bookDTO,book);
        book.setAuthor(author);
        bookRepository.save(book);
        return modelMapper.map(book,BookResponseDTO.class);
    }
    public List<BookResponseDTO> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book,BookResponseDTO.class)).toList();
    }
    public BookResponseDTO finBookById(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Nenhum livro identificado"));
        return modelMapper.map(book,BookResponseDTO.class);
    }
    public BookResponseDTO updateBook(Long authorId,BookResponseDTO bookDTO){
        Author author= authorRepository.findById(authorId).orElseThrow(()-> new EntityNotFoundException("Nenhum autor identificado"));
        Book book = bookRepository.findById(bookDTO.getId()).orElseThrow(()-> new EntityNotFoundException("Nenhum livro identificado"));
        book.setTitle(bookDTO.getTitle());
        book.setRealeseDate(bookDTO.getRealeseDate());
        book.setAuthor(author);
        bookRepository.save(book);
        return modelMapper.map(book,BookResponseDTO.class);
    }
    public void deleteBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Nenhum livro identificado"));
        bookRepository.delete(book);
    }
}
