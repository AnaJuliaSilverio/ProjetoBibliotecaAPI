package com.projetobibliotecaAPI.projetobibliotecaAPI.repositories;

import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
