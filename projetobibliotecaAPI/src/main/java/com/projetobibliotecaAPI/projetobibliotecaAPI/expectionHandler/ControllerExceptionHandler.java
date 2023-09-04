package com.projetobibliotecaAPI.projetobibliotecaAPI.expectionHandler;

import com.projetobibliotecaAPI.projetobibliotecaAPI.dtos.ExceptionDTO;
import com.projetobibliotecaAPI.projetobibliotecaAPI.models.BookUnavailableException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handlingOfDuplicateValues(ConstraintViolationException constraintViolationException){
        ExceptionDTO exceptionResponse = new ExceptionDTO(new Date(),"Dados inserido incorretamente");
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handlingOf404(EntityNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity handlingOfIncorrectDates(DateTimeException exception){
        ExceptionDTO exceptionResponse = new ExceptionDTO(new Date(),exception.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity handlingOfLoanStatus(BookUnavailableException bookUnavailableException){
        ExceptionDTO exceptionDTO = new ExceptionDTO(new Date(),bookUnavailableException.getMessage());
        return ResponseEntity.status(401).body(exceptionDTO);
    }
}