package com.projetobibliotecaAPI.projetobibliotecaAPI.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }
}
