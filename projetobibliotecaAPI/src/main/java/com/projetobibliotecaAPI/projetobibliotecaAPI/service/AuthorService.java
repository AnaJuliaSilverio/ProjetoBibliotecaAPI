package com.projetobibliotecaAPI.projetobibliotecaAPI.service;

import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorRequestDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.AuthorResponseDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Author;
import com.projetobibliotecaAPI.projetobibliotecaAPI.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AuthorResponseDTO createAuthor(AuthorRequestDTO authorRequestDTO){
        Author author = new Author();
        modelMapper.map(authorRequestDTO,author);
        authorRepository.save(author);
        return modelMapper.map(author,AuthorResponseDTO.class);
    }
    public AuthorResponseDTO getAuthorById(Long id){
        Author author= authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Nenhum autor identificado"));
        return modelMapper.map(author,AuthorResponseDTO.class);
    }
    public List<AuthorResponseDTO> getAllAuthor(){
        return authorRepository.findAll().stream()
                .map(author -> modelMapper.map(author,AuthorResponseDTO.class)).toList();
    }
    public AuthorResponseDTO upadteAuthor(AuthorResponseDTO authorResponseDTO){
        Author author= authorRepository.findById(authorResponseDTO.getId()).orElseThrow(()-> new EntityNotFoundException("Nenhum autor identificado"));
        author.setCpf(authorResponseDTO.getCpf());
        author.setName(authorResponseDTO.getName());
        author.setEmail(authorResponseDTO.getEmail());
        return modelMapper.map(author,AuthorResponseDTO.class);
    }
    public void deleteAuthor(Long id){
        Author author= authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Nenhum autor identificado"));
        authorRepository.delete(author);
    }
}
