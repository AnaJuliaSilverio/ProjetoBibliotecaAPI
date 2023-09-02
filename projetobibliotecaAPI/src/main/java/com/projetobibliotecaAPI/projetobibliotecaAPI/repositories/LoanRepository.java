package com.projetobibliotecaAPI.projetobibliotecaAPI.repositories;

import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Book;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findByBook(Book book);
}
