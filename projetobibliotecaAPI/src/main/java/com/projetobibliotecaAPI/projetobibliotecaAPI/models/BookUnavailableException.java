package com.projetobibliotecaAPI.projetobibliotecaAPI.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookUnavailableException extends RuntimeException{
    private static final long serialVersionUID =1L;

}
