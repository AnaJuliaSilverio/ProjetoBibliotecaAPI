package com.projetobibliotecaAPI.projetobibliotecaAPI.repositories;

import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
